package com.ldc.springsecurityswagger2.controller;

import com.ldc.springsecurityswagger2.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: ss
 * @time: 2020/4/24 22:38
 */
@RestController
@RequestMapping("/rest")
@Api(tags = "Rest风格测试接口")
public class UserController {

    @GetMapping("/hello")
    public String hello(String name) {
        return "hello " + name + " !";
    }

    @ApiOperation("根据id查询用户")
    @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "99")
    @GetMapping("/getUserById")
    public User getUserById(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @ApiOperation("根据id更新用户名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "99"),
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "javaboy")
    })
    @PostMapping("/updateUsernameById")
    public User updateUsernameById(String username, Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }

    @PostMapping("/")
    @ApiOperation("添加用户")
    public User addUser(@RequestBody User user) {
        return user;
    }

    @PostMapping("/deleteUserById")
    @ApiOperation("根据id 删除用户")
    public Object deleteUserById(@RequestParam Long id) {
        return id;
    }

}
