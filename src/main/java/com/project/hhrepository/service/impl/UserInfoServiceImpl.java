package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.LoginUser;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.UserInfo;
import com.project.hhrepository.mapper.UserInfoMapper;
import com.project.hhrepository.service.IUserInfoService;
import com.project.hhrepository.utils.CurrentUser;
import com.project.hhrepository.utils.DigestUtil;
import com.project.hhrepository.utils.TokenUtils;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Resource
    TokenUtils tokenUtils;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result userLogin(LoginUser loginUser) {
        //进行验证码配置
        String verificationCode = loginUser.getVerificationCode();
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(verificationCode))) {
            return Result.err(Result.CODE_ERR_BUSINESS, "验证码错误！！");
        }

        UserInfo userInfo = query().eq("userCode", loginUser.getUserCode()).one();
        if (userInfo != null)
            if (userInfo.getUserState().equals(WarehouseConstants.USER_STATE_PASS)) {//用户状态已审
                String userPwd = loginUser.getUserPwd();
                //进行md5加密
                String s = DigestUtil.hmacSign(userPwd);
                if (s.equals(userInfo.getUserPwd())) {//密码正确
                    CurrentUser currentUser = new CurrentUser(userInfo.getUserId(),
                            userInfo.getUserCode(), userInfo.getUserName());
                    //生成JWT Token
                    String token = tokenUtils.loginSign(currentUser, userInfo.getUserPwd());
                    return Result.ok("登录成功！", token);
                } else {//密码不正确
                    return Result.err(Result.CODE_ERR_BUSINESS, "密码错误");
                }
            } else {
                return Result.err(Result.CODE_ERR_BUSINESS, "用户未审核！");
            }
        else {
            return Result.err(Result.CODE_ERR_BUSINESS, "账号不存在");
        }
    }

    /**
     * 获取当前用户状态
     *
     * @param token 请求头中的token字段
     * @return 根据结果构造的Result对象
     */
    @Override
    public Result getCurrentUser(String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        return Result.ok(currentUser);
    }
}
