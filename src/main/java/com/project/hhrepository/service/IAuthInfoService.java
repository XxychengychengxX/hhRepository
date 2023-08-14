package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.AuthInfo;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IAuthInfoService extends IService<com.project.hhrepository.entity.AuthInfo> {

    /**
     * 根据传入的用户id获取当前登录所具有的权限(包含子级菜单和父级菜单)
     * @param userId 用户id
     * @return  当前登录用户所具有权限的list集合
     */
    List<AuthInfo> getAuthTreeByUid(Integer userId);

    /**
     * 查询所有权限树
     * @return 权限树
     */
    List<AuthInfo> getAuthTree();
}
