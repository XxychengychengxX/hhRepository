package com.project.hhrepository.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    /**
     * 查询所有权限树
     *
     * @return 权限树
     */
    @Override

    public List<AuthInfo> getAuthTree() {
        List<AuthInfo> list = query().ne("auth_type", 3)
                                     .eq("auth_state", 1)
                                     .list();

        return authListToTree(list, 0);
    }

    @Override
    public List<AuthInfo> getAuthTreeByUid(Integer userId) {
        //1.首先查找redis中是否已经有缓存的用户菜单树
        String authTreeStr = stringRedisTemplate.opsForValue()
                                                .get("authTree:" + userId);
        if (StringUtils.hasText(authTreeStr)) {//如果存在
            return JSONArray.parseArray(authTreeStr, AuthInfo.class);
        }
        //否则去查找用户权限下的所有菜单
        List<AuthInfo> authInfoByUid = baseMapper.getAuthByUid(userId);

        List<AuthInfo> authInfos = authListToTree(authInfoByUid, 0);

        //将内容保存到redis,这里不设置过期时间,保存的是每个用户自身的id
        stringRedisTemplate.opsForValue()
                           .set("authTree:" + userId, JSONArray.toJSONString(authInfos));


        //将所有菜单转成
        return authInfos;
    }

    /**
     * 深度优先搜索构建权限树
     *
     * @param allAuthListInfo 查出来的当前登录角色的
     * @param uid             当前要构建的父节点(即子树的parentId)
     * @return 当前登录用户所具有的权限树
     */

    //递归循环构建List<AuthInfo>
    private List<AuthInfo> authListToTree(List<AuthInfo> allAuthListInfo, Integer uid) {
        //首先查询每一个一级菜单
        //获取父权限(菜单)id为参数parentId的所有权限(菜单)
        //【parentId最初为0,即最初查的是所有一级权限(菜单)】
        List<AuthInfo> authList = new ArrayList<>();
        for (AuthInfo auth : allAuthListInfo) {
            /*
             * 表示就是父级菜单,例如第一个uid是0的时候,就找出所有parentId为 0 的项加入authList,后面再依次遍历authList的项并进行
             * 递归调用,一次构建当前祖宗的子树以及子树的子树,类似于深度优先搜索(DFS)
             */

            if (auth.getParentId() == uid) {
                authList.add(auth);
            }

        }
        /*
         * 构建子树的子树
         *
         */
        //查询List<Auth> authList中每个权限(菜单)的所有子级权限(菜单)
        for (AuthInfo auth : authList) {
            /*
                这里传入的第一个参数仍然要是查出来的allAuthListInfo表,因为装着当前登录角色所有的应有权限(无论是父级权限还是子级权限)，
                后者传入的是当前上级菜单中的每个元素的Id值,即下次递归要将所有
                allAuthListInfo中parentId为当前要寻找的auth.getAuthId() （--第二个参数）  ,也就是构建authId为auth.getAuthId()
                的子树
             */

            List<AuthInfo> childAuthList = authListToTree(allAuthListInfo, auth.getAuthId());
            auth.setChildAuth(childAuthList);

        }
        return authList;


    }
}
