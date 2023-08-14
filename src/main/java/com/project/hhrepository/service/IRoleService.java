package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.Role;
import com.project.hhrepository.page.Page;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IRoleService extends IService<Role> {

    /**
     * 获取所有用户的角色
     *
     * @return 封装了所有用户角色的List集合
     */
    List<Role> getRoleList();

    /**
     * 获取角色分页信息
     *
     * @param page 传入的page对象
     * @param role 传入的role对象
     * @return 封装了当前用户信息对象的Result对象
     */
    Page getRoleListByPage(Page page, Role role);

    /**
     * 添加角色
     *
     * @param role  角色对象
     * @param token jwt
     * @return Result对象
     */
    Result addRole(Role role, String token);
    /**
     * 修改角色的禁用状态
     *
     * @param role  角色对象
     * @param token jwt
     * @return Result对象
     */
    Boolean updateRoleState(Role role, String token);

    /**
     * 根据传入的角色id(角色对象)修改角色描述
     * @param role 角色描述
     * @param token jwt
     * @return 成功返回true,反之false
     */
    Boolean updateRoleDescById(Role role, String token);
}
