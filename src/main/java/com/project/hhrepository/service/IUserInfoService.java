package com.project.hhrepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.hhrepository.entity.LoginUser;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.myentity.User;
import com.project.hhrepository.entity.UserInfo;
import com.project.hhrepository.page.Page;

import java.util.List;

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

    /**
     * 获取用户分页信息
     *
     * @param page 传入的page对象
     * @param user 传入的user对象
     * @return 封装了当前用户信息对象的Result对象
     */
    Page getUserByPage(Page page, User user);

    /**
     * 添加用户的业务方法
     *
     * @param user  用户对象
     * @param token jwt令牌对象
     * @return 是否添加成功
     */
    Result addUser(UserInfo user, String token);

    /**
     * 更改用户启用状态
     *
     * @param userInfo 传入的用户对象
     * @param token    jwt令牌对象
     * @return Result对象
     */
    Result updateUserState(UserInfo userInfo, String token);

    Boolean setDeleteStateByUids(List<Integer> userIds);

    /**
     * 根据用用户id修改用户的用户名
     *
     * @param userInfo 封装了用户id与预修改的用户名的对象
     * @param token    jwt对象
     * @return 成功返回true, 反之返回false
     */
    Boolean updateUserByUid(UserInfo userInfo, String token);

    /**
     * 根据用户id重置它的密码为123456
     *
     * @param userId 传入的userId
     * @param token  jwt
     * @return 成功返回true, 反之false
     */
    Boolean updatePwdByUid(Integer userId, String token);

    /**
     * 根据用户id来查找他的权限
     * @param userId userId
     * @return Result对象
     */
    Result getUserAuthByUserId(Integer userId);

    /**
     * 直接修改用户权限(不能)
     * @return result对象
     */
    Result updateUserAuth();
}
