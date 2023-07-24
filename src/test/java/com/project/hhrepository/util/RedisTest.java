/**
 * @author Valar Morghulis
 * @Date 2023/7/24
 */
package com.project.hhrepository.util;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.StringUtils;

@SpringBootTest
@TestPropertySource(properties = {
        "logging.level.root=WARN"
})
public class RedisTest {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    void setTest() {
        stringRedisTemplate.opsForValue().set("k1", "v1");
        String value = stringRedisTemplate.opsForValue().get("k1");
        System.out.println("value = " + value);
    }


    @Test
    void getTest() {
        String value = stringRedisTemplate.opsForValue().get("k3");
        boolean b = StringUtils.hasText(value);
        System.out.println("b = " + b);
        System.out.println("value = " + value);
    }
}
