package com.ldc.userserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @description:
 * @author: ss
 * @time: 2020/4/25 0:38
 */
@RestController
public class UserController {

    @GetMapping("/user")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}
