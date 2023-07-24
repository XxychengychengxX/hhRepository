package com.project.hhrepository.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.Auth;
import com.project.hhrepository.entity.AuthInfo;
import com.project.hhrepository.mapper.AuthInfoMapper;
import com.project.hhrepository.service.IAuthInfoService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
public class AuthInfoServiceImpl extends ServiceImpl<AuthInfoMapper, AuthInfo> implements IAuthInfoService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Auth> getAuthTreeByUid(Integer userId) {
        //1.首先查找redis中是否已经有缓存的用户菜单树
        String authTreeStr = stringRedisTemplate.opsForValue().get("authTree:" + userId);
        if (StringUtils.hasText(authTreeStr)) {//如果不存在
            return JSONArray.parseArray(authTreeStr, Auth.class);
        }
        //否则去查找用户权限下的所有菜单
        List<Auth> authByUid = baseMapper.findAuthByUid(userId);

        List<Auth> auths = authListToTree(authByUid, 0);

        //将内容保存到redis
        stringRedisTemplate.opsForValue().set("authTree:" + userId, JSONArray.toJSONString(auths));


        //将所有菜单转成
        return null;
    }

    //递归循环构建List<Auth>
    private List<Auth> authListToTree(List<Auth> allAuthList, Integer uid) {
        //首先查询每一个一级菜单
        ArrayList<Auth> firstLevelAuthList = new ArrayList<>();
        allAuthList.forEach(auth -> {
            if (auth.getParentId() == uid)
                firstLevelAuthList.add(auth);
        });


        //再查询每一个一级菜单下的二级菜单
        firstLevelAuthList.forEach(fistAuth -> {
            List<Auth> auths = authListToTree(allAuthList, fistAuth.getParentId());
            fistAuth.setChildAuth(auths);
        });

        return firstLevelAuthList;
    }
}
