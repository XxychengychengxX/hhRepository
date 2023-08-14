package com.project.hhrepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

//开启Redis注解版缓存
@EnableCaching
@SpringBootApplication

@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"}, allowCredentials
        = "true")
public class HhRepositoryApplication {

    public static void main(String[] args) {


        SpringApplication.run(HhRepositoryApplication.class, args);
    }

}
