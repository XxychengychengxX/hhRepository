package com.project.hhrepository.mapper;

import com.alibaba.fastjson.JSON;
import com.project.hhrepository.entity.AuthInfo;
import com.project.hhrepository.entity.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class AuthInfoMapperTest {

    @Resource
    AuthInfoMapper authInfoMapper;

    @Test
    void findAuthByUid() {
        List<AuthInfo> authByUid = authInfoMapper.getAuthByUid(1);
        Result ok = Result.ok(authByUid);


        log.warn(JSON.toJSONString(ok));
    }
}