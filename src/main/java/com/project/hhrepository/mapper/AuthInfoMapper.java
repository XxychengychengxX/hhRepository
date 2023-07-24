package com.project.hhrepository.mapper;

import com.project.hhrepository.entity.Auth;
import com.project.hhrepository.entity.AuthInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@Mapper
public interface AuthInfoMapper extends BaseMapper<AuthInfo> {

    List<Auth> findAuthByUid(int userID);
}
