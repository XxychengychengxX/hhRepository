package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.dto.AssignRoleDto;
import com.project.hhrepository.entity.Role;
import com.project.hhrepository.entity.UserRole;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IUserRoleService extends IService<UserRole> {
    /**
     * 根据用户id获取用户角色
     *
     * @param userId 用户id
     * @return 封装了UserRole的List集合
     */
    List<Role> getUserRoleListByUserId(Integer userId);


    /**
     * 根据传入的用户id和用户角色名来更改当前用户的角色信息
     *
     * @param assignRoleDto 封装了当前用户id和即将更改的角色信息的对象
     * @return 成功返回true反之false
     */
    Boolean assignUserRoleByUid(AssignRoleDto assignRoleDto);

    /**
     * 根据roleid 删除用户与角色的对应关系
     * @param roleId 传入的roleid
     * @return 成功返回true 反之false
     */
    Boolean removeByRoleId(Integer roleId);
}
