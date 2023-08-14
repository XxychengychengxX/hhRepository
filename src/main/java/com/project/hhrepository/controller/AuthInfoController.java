package com.project.hhrepository.controller;

import com.project.hhrepository.entity.AuthInfo;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.service.IAuthInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:9988"})

public class AuthInfoController {

    @Resource
    IAuthInfoService authInfoService;


    @GetMapping("/auth-tree")
    public Result getAuthTree(){
        List<AuthInfo> authTree = authInfoService.getAuthTree();
        return Result.ok(authTree);
    }
}
