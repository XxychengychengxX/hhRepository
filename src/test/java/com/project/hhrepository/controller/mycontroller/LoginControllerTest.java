package com.project.hhrepository.controller.mycontroller;

import com.alibaba.fastjson.JSON;
import com.project.hhrepository.entity.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
@Slf4j

class LoginControllerTest {

    @Resource
    LoginController loginController;

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Test
    void getAuthTree() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJDTEFJTV9OQU1FX1VTRVJDT0RFIjoiYWRtaW4iLCJDTEFJTV9OQU1FX1VTRVJJRCI6MSwiQ0xBSU1fTkFNRV9VU0VSTkFNRSI6Iui2hee6p-euoeeQhuWRmCIsImV4cCI6MTY5MTg0NzcyOCwiaWF0IjoxNjkxODQ3NzI4fQ.rYfS1ENrFgHqRTfr-zFUWIti_OIIM9AZ077F_trdASE";
        Result authTree = loginController.getAuthTree(token);

        log.warn(JSON.toJSONString(authTree));
    }
}