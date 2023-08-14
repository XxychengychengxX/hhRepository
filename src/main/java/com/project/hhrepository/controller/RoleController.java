package com.project.hhrepository.controller;

import com.project.hhrepository.dto.RoleAuthDto;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.Role;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IRoleAuthService;
import com.project.hhrepository.service.IRoleService;
import com.project.hhrepository.service.IUserRoleService;
import com.project.hhrepository.utils.TokenUtils;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:9988"})
@RequestMapping("/role")
public class RoleController {

    @Resource
    IRoleService roleService;

    @Resource
    IRoleAuthService roleAuthService;

    @Resource
    TokenUtils tokenUtils;
    @Resource
    IUserRoleService userRoleService;


    @GetMapping("/role-list")
    public Result getRoleList() {
        List<Role> roleList = roleService.getRoleList();
        return Result.ok(roleList);
    }

    @GetMapping("/role-page-list")
    public Result getRoleListByPage(Page page, Role role) {
        //这里没有错误处理
        return Result.ok(roleService.getRoleListByPage(page, role));

    }

    @PostMapping("/role-add")
    public Result addRole(@RequestBody Role role,
                          @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        return roleService.addRole(role, token);
    }

    @PutMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Boolean aBoolean = roleService.updateRoleState(role, token);
        if (aBoolean)
            return Result.ok("添加角色成功");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "添加失败,请检查服务器后重试");

    }

    @DeleteMapping("/role-delete/{roleId}")
    public Result deleteRoleByRoleId(@PathVariable Integer roleId) {
        boolean b = roleService.removeById(roleId);
        if (b) {
            roleAuthService.removeByRoleId(roleId);
            userRoleService.removeByRoleId(roleId);

            return Result.ok("删除角色成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "删除角色失败,请检查服务器后重试!");
    }

    @PutMapping("/role-update")
    public Result updateRoleDescById(@RequestBody Role role,
                                     @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Boolean aBoolean = roleService.updateRoleDescById(role, token);
        if (aBoolean)
            return Result.ok("修改角色成功");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "修改角色失败,请检查服务器后重试");

    }

    @GetMapping("/role-auth")
    public Result getRoleAuth(@RequestParam Integer roleId) {
        return roleAuthService.getRoleAuthByRoleId(roleId);
    }

    @PutMapping("/auth-grant")
    public Result updateAuthGrant(@RequestBody RoleAuthDto roleAuthDto) {

        return roleAuthService.updateAuthGrant(roleAuthDto);
    }
}
