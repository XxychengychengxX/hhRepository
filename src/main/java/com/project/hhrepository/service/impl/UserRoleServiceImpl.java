package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.dto.AssignRoleDto;
import com.project.hhrepository.entity.Role;
import com.project.hhrepository.entity.UserRole;
import com.project.hhrepository.mapper.RoleMapper;
import com.project.hhrepository.mapper.UserInfoMapper;
import com.project.hhrepository.mapper.UserRoleMapper;
import com.project.hhrepository.service.IUserRoleService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@CacheConfig(cacheNames = "com.project.hhrepository.service.impl.UserRoleServiceImpl")
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {


    //中间表链接表注入两个链接表的mapper的对象
    @Resource
    RoleMapper roleMapper;

    @Resource
    UserInfoMapper userInfoMapper;

    /**
     * 根据传入的用户id和用户角色名来更改当前用户的角色信息
     *
     * @param assignRoleDto 封装了当前用户id和即将更改的角色信息的对象
     * @return 成功返回true反之false
     */
    @Override
    @CacheEvict(value = "all:role",key = "#assignRoleDto.userId")
    public Boolean assignUserRoleByUid(AssignRoleDto assignRoleDto) {
        boolean flag = true;
        //首先拿到当前的用户id和要更改后的用户角色 角色名
        Integer userId = assignRoleDto.getUserId();
        //删除当前用户的所有存在的角色分配
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", userId);
        //如果删除成功,才进行接下来的获取对象
        if (remove(userRoleQueryWrapper)) {
            //查询出要更改的角色名
            List<String> roleCheckList = assignRoleDto.getRoleCheckList();
            QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.in("role_name", roleCheckList);
            //使用Stream流来只查询一列
            List<Integer> rolesId = roleMapper.selectList(roleQueryWrapper).stream().map(Role::getRoleId).toList();
            //直接插入就完事了

            rolesId.forEach((roid) -> {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roid);
                userRole.setUserId(userId);
                int insert = baseMapper.insert(userRole);

            });
        } else {
            flag = false;
        }

        return flag;

    }

    /**
     * 根据roleid 删除用户与角色的对应关系
     *
     * @param roleId 传入的roleid
     * @return 成功返回true 反之false
     */
    @Override
    public Boolean removeByRoleId(Integer roleId) {
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();

        userRoleQueryWrapper.eq("role_id", roleId);

        return remove(userRoleQueryWrapper);
    }

    /**
     * 根据用户id获取用户角色
     *
     * @param userId 用户id
     * @return 封装了UserRole的List集合
     */
    @Cacheable(value = "all:role", key = "#userId")
    @Override
    public List<Role> getUserRoleListByUserId(Integer userId) {
        List<Integer> roleIds = query().eq("user_id", userId).list().stream().map(UserRole::getRoleId).toList();
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        if (!roleIds.isEmpty()) {
            roleQueryWrapper.in("role_id", roleIds);
            return roleMapper.selectList(roleQueryWrapper);
        }
        return new ArrayList<>();

    }
}
