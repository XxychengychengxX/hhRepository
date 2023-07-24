package com.project.hhrepository.service;

import com.project.hhrepository.entity.Auth;
import com.project.hhrepository.entity.AuthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
public interface IAuthInfoService extends IService<AuthInfo> {
    List<Auth> getAuthTreeByUid(Integer userId);

}
