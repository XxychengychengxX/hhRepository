package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.LoginUser;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.UserInfo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 用户登录
     *
     * @param loginUser 登录封装
     * @return 根据结果构造的Result对象
     */
    Result userLogin(LoginUser loginUser);

    /**
     * 获取当前用户状态
     *
     * @param token 请求头中的token字段
     * @return 根据结果构造的Result对象
     */
    Result getCurrentUser(String token);
}
