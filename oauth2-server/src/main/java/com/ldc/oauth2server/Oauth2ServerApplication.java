package com.ldc.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
//去掉此注解，单独部署资源服务
//@EnableResourceServer//去掉此注解访问client1会报重定向的次数过多；加上此注解后台会打印出来随机生成的密码
public class Oauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }

}
