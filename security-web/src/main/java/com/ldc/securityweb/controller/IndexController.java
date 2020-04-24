package com.ldc.securityweb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ss
 * @time: 2020/4/22 21:16
 */
@RestController
public class IndexController {

    @PostMapping(value = {"/","/index"})
    public String index() {
        return "hello index";
    }

    @PostMapping(value = {"/user"})
    public String user() {
        return "hello user";
    }

    @PostMapping("/admin")
    public Object admin() {
        return "hello admin";
    }

    //测试一  通过SecurityContextHolder获取登录认证主体
    @RequestMapping("/userinfo")
    public Object userinfo() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    //测试二  通过传Authentication获取登录认证主体
    @RequestMapping("/userinfo2")
    public Object userinfo2(Authentication authentication) {
        return authentication.getPrincipal();
    }

}
