package com.project.hhrepository.controller;

import com.project.hhrepository.dto.AssignRoleDto;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.Role;
import com.project.hhrepository.entity.UserInfo;
import com.project.hhrepository.entity.myentity.User;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IAuthInfoService;
import com.project.hhrepository.service.IUserInfoService;
import com.project.hhrepository.service.IUserRoleService;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@RestController
//@RequestMapping("/user-info")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:9988"})
@RequestMapping("/user")
@CacheConfig
@Transactional
public class UserInfoController {

    @Resource
    IUserInfoService userInfoService;

    @Resource
    IUserRoleService userRoleService;
    @Resource
    IAuthInfoService authInfoService;

    @GetMapping("/user-auth")
    public Result getUserAuth(@RequestParam Integer userId) {
        return userInfoService.getUserAuthByUserId(userId);
    }
    @PutMapping("/auth-grant")
    public Result updateUserAuth() {
       return userInfoService.updateUserAuth();
    }

    /**
     * 根据分页条件,传入参数获取满足参数条件的用户列表
     *
     * @param page 分页条件
     * @param user 查询传入参数
     * @return Result对象
     */
    @GetMapping("/user-list")
    public Result getUserByPage(
            /*
             * http://localhost:9988/api/user/user-list?userCode=&userType=&userState=&pageSize=5&pageNum=1&totalNum
             * =0&_t
             * =1691758445642
             * @RequestParam不能标记在对象上以对象来封装？后面的值,要获取
             * 1. 用@RequestParam依次获取,但是这样子很麻烦
             * 2， 直接使用对象来获取,不用添加任何注解,但是对象中的成员变量名要与路径中的一致
             */
            Page page, User user) {
        page = userInfoService.getUserByPage(page, user);
        return Result.ok(page);
    }

    /**
     * 添加用户的接口
     *
     * @param user 添加的用户信息
     * @return Result对象
     */
    @PostMapping("/addUser")
    public Result addUser(@RequestBody UserInfo user,
                          @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        return userInfoService.addUser(user, token);

    }

    /**
     * 更改用户可用状态
     *
     * @param userInfo 传入的当前用户的信息
     * @param token    令牌token,用于设置用户的update_by
     * @return Result对象
     */
    @PutMapping("/updateState")
    public Result updateUserState(@RequestBody UserInfo userInfo,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        return userInfoService.updateUserState(userInfo, token);
    }

    /**
     * 根据用户id获取他当前被分配角色
     *
     * @param userId 用户id
     * @return Result对象
     */
    @GetMapping("/user-role-list/{userId}")
    public Result getUserRoleListByUserId(@PathVariable Integer userId) {
        List<Role> userRoleListByUserId = userRoleService.getUserRoleListByUserId(userId);
   /*
        即使是空也要返回
            if (userRoleListByUserId.isEmpty())
            return Result.err(Result.CODE_ERR_BUSINESS, "查询当前用户角色出错,请稍后再试");
            */
        return Result.ok(userRoleListByUserId);
    }

    /**
     * 分配用户角色
     *
     * @param assignRoleDto 封装了用户id和预分配角色名List的对象
     * @return Result对象
     */
    @PutMapping("/assignRole")
    public Result assignUserRole(@RequestBody AssignRoleDto assignRoleDto) {
        Boolean aBoolean = userRoleService.assignUserRoleByUid(assignRoleDto);
        if (aBoolean)
            return Result.ok("分配用户角色成功");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "分配失败,请检查服务器后重试");
    }

    /**
     * 根据用户id删除角色(更改is_delete字段)
     *
     * @param userId 用户id
     * @return Result对象
     */
    @DeleteMapping("/deleteUser/{userId}")
    public Result updateIsDeleteByUid(@PathVariable Integer userId) {
        List<Integer> userid = List.of(userId);
        Boolean aBoolean = userInfoService.setDeleteStateByUids(userid);
        if (aBoolean)
            return Result.ok("删除成功!");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "删除失败!请检查服务器后重试");
    }


    /**
     * 根据批量传入的用户id删除所选用户
     *
     * @param userIds 批量传入的用户id
     * @return Result对象
     */
    @DeleteMapping("/deleteUserList")
    public Result updateIsDeleteListByUids(@RequestBody List<Integer> userIds) {
        Boolean aBoolean = userInfoService.setDeleteStateByUids(userIds);
        if (aBoolean)
            return Result.ok("删除成功!");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "删除失败!请检查服务器后重试");

    }

    @PutMapping("/updateUser")
    public Result updateUserByUid(@RequestBody UserInfo userInfo,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Boolean aBoolean = userInfoService.updateUserByUid(userInfo, token);
        if (aBoolean)
            return Result.ok("更新成功!");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "更新失败!请检查服务器后重试");

    }

    @PutMapping("/updatePwd/{userId}")
    public Result updatePwdByUid(@PathVariable Integer userId,
                                 @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Boolean aBoolean = userInfoService.updatePwdByUid(userId, token);
        if (aBoolean)
            return Result.ok("重置密码成功!");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "重置密码失败!请检查服务器后重试");
    }
}
