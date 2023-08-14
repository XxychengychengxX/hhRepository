package com.project.hhrepository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.hhrepository.entity.AuthInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    List<AuthInfo> getAuthByUid(@Param("userId") int userId);


    List<AuthInfo> getAuthTree();
}
