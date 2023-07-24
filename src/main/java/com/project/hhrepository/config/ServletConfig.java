/**
 * @author Valar Morghulis
 * @Date 2023/7/24
 */
package com.project.hhrepository.config;

import com.project.hhrepository.filter.LoginCheckFilter;
import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class ServletConfig {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    /*配置FilterRegistration的bean对象*/
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean =
                new FilterRegistrationBean<>();

        //这里要进行手动注入
        LoginCheckFilter loginCheckFilter = new LoginCheckFilter();
        loginCheckFilter.setRedisTemplate(stringRedisTemplate);

        filterFilterRegistrationBean.setFilter(loginCheckFilter);

        return filterFilterRegistrationBean;
    }
}
