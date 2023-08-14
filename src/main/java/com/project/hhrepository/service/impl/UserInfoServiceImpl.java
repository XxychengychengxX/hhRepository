package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.AuthInfo;
import com.project.hhrepository.entity.LoginUser;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.UserInfo;
import com.project.hhrepository.entity.myentity.User;
import com.project.hhrepository.mapper.AuthInfoMapper;
import com.project.hhrepository.mapper.UserInfoMapper;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IUserInfoService;
import com.project.hhrepository.utils.CurrentUser;
import com.project.hhrepository.utils.DigestUtil;
import com.project.hhrepository.utils.TokenUtils;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Service
@Transactional
@CacheConfig(cacheNames = "com.project.hhrepository.service.impl.UserInfoServiceImpl")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Resource
    TokenUtils tokenUtils;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    AuthInfoMapper authInfoMapper;

    @Override
    public Result userLogin(LoginUser loginUser) {
        //进行验证码配置
        String verificationCode = loginUser.getVerificationCode();
        //这里是判断redis中是否有存在该验证码(注意这里验证码是作为key而不是作为value)
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(verificationCode))) {
            return Result.err(Result.CODE_ERR_BUSINESS, "验证码错误！！");
        }

        UserInfo userInfo = query().eq("user_code", loginUser.getUserCode())
                                   .one();
        if (userInfo != null)
            if (userInfo.getUserState()
                        .equals(WarehouseConstants.USER_STATE_PASS)) {//用户状态已审
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

    /**
     * 获取用户分页信息
     *
     * @param page 传入的page对象
     * @param user 传入的user对象
     * @return 封装了当前用户信息对象的Result对象
     */
    //    @Cacheable(key = "'all:user'")这里不加这个注解,因为翻页是需要重新查找的,而不是进行缓存
    @Override
    public Page getUserByPage(Page page, User user) {
        //如果不为空
        QueryWrapper<UserInfo> userInfoWrapper = new QueryWrapper<>();
        userInfoWrapper.like(user.getUserCode() != null && !(user.getUserCode()
                                                                 .equals("")), "user_code",
                               user.getUserCode())
                       .eq(user.getUserType() != null && !(user.getUserType()
                                                               .equals("")), "user_type",
                               user.getUserType())
                       .like(user.getUserState() != null && !(user.getUserState()
                                                                  .equals("")), "user_state",
                               user.getUserState());
        //要求查找没有被删除的（删除状态为0）
        userInfoWrapper.eq("is_delete", 0);
        //传入前端传来的user信息来换行
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserInfo> userInfoPage =
                new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page.getPageNum(), page.getPageSize());
       /*
        page方法是查询出所有满足条件的对象并且封装了
        查询结果，分页信息等对象到userInfoPage中
        userInfoPage类型是com.baomidou.mybatisplus.extension.plugins.pagination.Page
        如下：
        private static final long serialVersionUID = 8545996863226528798L;
        protected List<T> records; ------查询结果
        protected long total;  -----总数
        protected long size;  ------当前包含的数目
        protected long current; --------当前页
        protected List<OrderItem> orders;
        protected boolean optimizeCountSql;
        protected boolean searchCount;
        protected boolean optimizeJoinOfCountSql;
        protected Long maxLimit;
        protected String countId;
        */
        page(userInfoPage, userInfoWrapper);

        //分页查询出来的满足条件的List集合
        List<UserInfo> records = userInfoPage.getRecords();

        //设置参数信息（响应给前端）
        page.setResultList(records);
        page.setTotalNum((int) userInfoPage.getTotal());

        return page;
    }

    /**
     * 更改用户启用状态
     *
     * @param userInfo 传入的用户对象
     * @param token    jwt令牌对象
     * @return Result对象
     */
    @Override
    // @CacheEvict(value = "all:user",key = "")
    public Result updateUserState(UserInfo userInfo, String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        //设置更改人的id
        userInfo.setUpdateBy(userId);

        boolean b = updateById(userInfo);
        if (b)
            return Result.ok("改变状态成功");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "更改失败,可能是服务器内部错误");
    }

    //@CacheEvict(value = "all:user")
    @Override
    public Boolean setDeleteStateByUids(List<Integer> userIds) {

        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("user_id", userIds)
                     .set("is_delete", "1");

        return update(null, updateWrapper);
    }

    /**
     * 根据用户id重置它的密码为123456
     *
     * @param userId 传入的userId
     * @param token  jwt
     * @return 成功返回true, 反之false
     */
    //@CacheEvict(value = "'all:user'")
    @Override
    public Boolean updatePwdByUid(Integer userId, String token) {
        UserInfo userInfo = new UserInfo();


        //首先获取当前系统操作用户的用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int currentUserUserId = currentUser.getUserId();

        userInfo.setUpdateBy(currentUserUserId);
        userInfo.setUpdateTime(LocalDateTime.now());

        userInfo.setUserId(userId);
        //将密码加密后重置为123456
        String userPwd = DigestUtil.hmacSign("123456");
        userInfo.setUserPwd(userPwd);


        return updateById(userInfo);
    }

    /**
     * 直接修改用户权限(不能)
     *
     * @return result对象
     */
    @Override
    @Cacheable(key = "'rsponse:updateUserAuth'")
    public Result updateUserAuth() {
        return Result.err(Result.CODE_ERR_BUSINESS, "您不能直接更改用户权限,要通过修改用户的角色分配来达到此效果");

    }

    /**
     * 根据用户id来查找他的权限
     *
     * @param userId userId
     * @return Result对象
     */
    public Result getUserAuthByUserId(Integer userId) {

        List<Integer> authInfos = authInfoMapper.getAuthByUid(userId)
                                                .stream()
                                                .map(AuthInfo::getAuthId)
                                                .toList();
        return Result.ok(authInfos);
    }

    /**
     * 根据用用户id修改用户的用户名
     *
     * @param userInfo 封装了用户id与预修改的用户名的对象
     * @param token    jwt对象
     * @return 成功返回true, 反之返回false
     */
    // @CacheEvict(value = "'all:user'")
    @Override
    public Boolean updateUserByUid(UserInfo userInfo, String token) {
        //首先获取当前系统操作用户的用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int currentUserUserId = currentUser.getUserId();

        userInfo.setUpdateBy(currentUserUserId);
        userInfo.setUpdateTime(LocalDateTime.now());

        return updateById(userInfo);

    }

    /**
     * 添加用户的业务方法
     *
     * @param userInfo 用户对象
     * @param token    jwt令牌对象
     * @return 是否添加成功
     */
    //@CacheEvict(value = "'all:user'")
    @Override
    public Result addUser(UserInfo userInfo, String token) {

        //首先判断账号是否存在
        //chainWrapper可以不通过basemapper直接查询，而非chainwrapper则需要
        QueryChainWrapper<UserInfo> query = query();
        UserInfo userInDataBase = query.eq("user_code", userInfo.getUserCode())
                                       .one();
        if (userInDataBase != null)
            return Result.err(Result.CODE_ERR_BUSINESS, "用户名已存在!");

        //如果用户名不存在
        //首先获取用户密码
        String userPwd = DigestUtil.hmacSign(userInfo.getUserPwd());
        //然后将密码设置成MD5加密后的密码
        userInfo.setUserPwd(userPwd);
        //然后查询当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //设置该用户被谁所创建
        userInfo.setCreateBy(currentUser.getUserId());
        userInfo.setUpdateBy(currentUser.getUserId());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfo.setCreateTime(LocalDateTime.now());

        userInfo.setIsDelete("0");
        userInfo.setUserState("0");

        boolean save = save(userInfo);

        if (save)
            return Result.ok("添加用户成功!(新添加用户仍处于禁用状态)");
        return Result.err(Result.CODE_ERR_BUSINESS, "添加失败,可能是服务器出错");


    }
}
