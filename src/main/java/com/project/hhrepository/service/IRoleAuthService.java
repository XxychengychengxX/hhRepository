package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.dto.RoleAuthDto;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.RoleAuth;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IRoleAuthService extends IService<RoleAuth> {

    /**
     * 根据roleId来删除中间表
     *
     * @param roleId 传入的角色id
     * @return 成功返回true
     */
    boolean removeByRoleId(Integer roleId);

    /**
     * 根据角色id来获取对应的权限信息
     *
     * @param roleId role id
     * @return Result对象
     */
    Result getRoleAuthByRoleId(Integer roleId);

    /**
     * 根据角色id和预分配的角色权限来修改权限
     *
     * @param roleAuthDto 装有角色id和预分配的角色权限的项
     * @return Result对象
     */
    Result updateAuthGrant(RoleAuthDto roleAuthDto);
}
