package com.project.hhrepository.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.Role;
import com.project.hhrepository.mapper.RoleMapper;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IRoleService;
import com.project.hhrepository.utils.CurrentUser;
import com.project.hhrepository.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@CacheConfig(cacheNames = "com.project.hhrepository.service.impl.RoleServiceImpl")
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Resource
    TokenUtils tokenUtils;

    /**
     * 获取所有用户角色
     *
     * @return 封装了所有用户角色的List集合
     */
    @Cacheable(value = "all:activeRolelist")//使用redis进行活跃缓存
    @Override
    public List<Role> getRoleList() {
        QueryChainWrapper<Role> query = query();

        return query.eq("role_state", 1).list();
    }

    /**
     * 根据传入的角色id(角色对象)修改角色描述
     *
     * @param role  角色描述
     * @param token jwt
     * @return 成功返回true, 反之false
     */
    @CacheEvict(value = {"all:roleListPage","all:activeRolelist"},allEntries = true)
    //更改角色表后,不仅要删除角色表的缓存,还要删除活跃角色表的缓存
    @Override
    public Boolean updateRoleDescById(Role role, String token) {
        //首先获取当前操作用户的用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        //然后进行设置操作等
        role.setUpdateBy(userId);

        return updateById(role);
    }

    /**
     * 修改角色的禁用状态
     *
     * @param role  角色对象
     * @param token jwt
     * @return Result对象
     */
    @Override
    //这里开启allEntries之后,删除所有value指定的缓存名(有key就考虑key,否则删除所有)
    @CacheEvict(value = {"all:roleListPage","all:activeRolelist"},allEntries = true)
    public Boolean updateRoleState(Role role, String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();

        role.setUpdateBy(userId);
        role.setUpdateTime(LocalDateTime.now());

        return updateById(role);
    }

    /**
     * 添加用户
     *
     * @param role  角色对象
     * @param token jwt
     * @return Result对象
     */
    @Override
    @CacheEvict(value = {"all:roleListPage","all:activeRolelist"},allEntries = true)
    public Result addRole(Role role, String token) {
        Role one = query().eq("role_name", role.getRoleName()).or().eq("role_code", role.getRoleCode()).one();
        if (one != null)
            return Result.err(Result.CODE_ERR_BUSINESS, "用户已存在");
        //获取当前操作的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int currentUserUserId = currentUser.getUserId();

        //设置一些更新属性
        role.setCreateBy(currentUserUserId);
        role.setUpdateTime(LocalDateTime.now());
        role.setUpdateBy(currentUserUserId);
        role.setCreateTime(LocalDateTime.now());
        //设置为未启用
        role.setRoleState("0");

        boolean save = save(role);

        if (save)
            return Result.ok("添加角色成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "添加角色失败,请检查后重试");

    }

    /**
     * 获取用户分页信息
     *
     * @param page 传入的page对象
     * @param role 传入的role对象
     * @return 封装了当前用户信息对象的Result对象
     */
    @Override
    @Cacheable(value = "all:roleListPage")
    public Page getRoleListByPage(Page page, Role role) {
        //如果不为空
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        /*
        需要说明的是，这里的like查询是使用的默认方式，也就是说在查询条件的左右两边都有%：NAME = ‘%王%"；
        如果只需要在左边或者右边拼接%，可以使用likeLeft或者likeRight方法。


        这里，like方法有三个参数：

        第一个参数：该参数是一个布尔类型，只有该参数是true时，才将like条件拼接到sql中；
        本例中，如果name字段不为空，则拼接name字段的like查询条件；
        第二个参数：该参数是数据库中的字段名；
        第三个参数：该参数值字段值；
        */

        roleQueryWrapper.like(role.getRoleName() != null && !(role.getRoleName().equals("")), "role_name",
                role.getRoleName());

        roleQueryWrapper.like((role.getRoleCode() != null && !(role.getRoleCode().equals(""))), "role_code",
                role.getRoleCode());


        roleQueryWrapper.eq((role.getRoleState() != null && !(role.getRoleState().equals(""))), "role_state",
                role.getRoleState());
        //要求查找没有被删除的（删除状态为0）
        //roleQueryWrapper.eq("is_delete", 0);
        //传入前端传来的user信息来换行
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Role> rolePage =
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
        page(rolePage, roleQueryWrapper);

        //分页查询出来的满足条件的List集合
        List<Role> records = rolePage.getRecords();

        //设置参数信息（响应给前端）
        page.setResultList(records);
        page.setTotalNum((int) rolePage.getTotal());

        return page;
    }
}
