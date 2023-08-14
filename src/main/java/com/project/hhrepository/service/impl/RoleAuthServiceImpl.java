package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.dto.RoleAuthDto;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.RoleAuth;
import com.project.hhrepository.entity.UserRole;
import com.project.hhrepository.mapper.RoleAuthMapper;
import com.project.hhrepository.mapper.UserRoleMapper;
import com.project.hhrepository.service.IRoleAuthService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
@Transactional
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthMapper, RoleAuth> implements IRoleAuthService {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    UserRoleMapper userRoleMapper;

    /**
     * 根据角色id和预分配的角色权限来修改权限
     *
     * @param roleAuthDto 装有角色id和预分配的角色权限的项
     * @return Result对象
     */
    @Override
    public Result updateAuthGrant(RoleAuthDto roleAuthDto) {
        boolean flag = true;

        List<Integer> authIds = roleAuthDto.getAuthIds();
        Integer roleId = roleAuthDto.getRoleId();

        //首先查到当前的对象
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("role_id", roleId);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);
        if (!userRoles.isEmpty()) {
            userRoles.forEach(userRole -> {
                Integer userId = userRole.getUserId();
                stringRedisTemplate.delete("authTree:" + userId);
            });
        }

        QueryWrapper<RoleAuth> roleAuthQueryWrapper = new QueryWrapper<>();
        roleAuthQueryWrapper.eq("role_id", roleId);
        remove(roleAuthQueryWrapper);


        for (Integer authId : authIds) {
            RoleAuth roleAuth = new RoleAuth();
            roleAuth.setRoleId(roleId);
            roleAuth.setAuthId(authId);
            boolean save = save(roleAuth);
            if (!save) {
                flag = false;
                break;
            }
        }
        if (flag)
            return Result.ok("修改角色权限分配成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "修改角色分配失败,请检查服务器后重试");
    }

    /**
     * 根据角色id来获取对应的权限信息
     *
     * @param roleId role id
     * @return Result对象
     */
    @Override
    public Result getRoleAuthByRoleId(Integer roleId) {
        List<Integer> roleId1 = query().eq("role_id", roleId)
                                       .list()
                                       .stream()
                                       .map(RoleAuth::getAuthId)
                                       .toList();
        return Result.ok(roleId1);
    }

    /**
     * 根据roleId来删除中间表
     *
     * @param roleId 传入的角色id
     * @return 成功返回true反之false
     */
    @Override
    public boolean removeByRoleId(Integer roleId) {
        QueryWrapper<RoleAuth> roleAuthQueryWrapper = new QueryWrapper<>();
        roleAuthQueryWrapper.eq("role_id", roleId);
        return remove(roleAuthQueryWrapper);
    }
}
