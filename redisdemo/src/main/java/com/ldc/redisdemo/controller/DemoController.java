package com.ldc.redisdemo.controller;

import com.alibaba.fastjson.TypeReference;
import com.ldc.redisdemo.common.RedisUtil;
import com.ldc.redisdemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/27 20:31
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    @Autowired
    private RedisUtil redisUtil;

    //get请求
    @RequestMapping(value = "/set1", method = RequestMethod.GET)
    public Object set1(@RequestParam Long id) {
        redisUtil.set("set1Key","123456");
        return redisUtil.get("set1Key");
    }

    //get请求
    @RequestMapping(value = "/set2", method = RequestMethod.GET)
    public Object set2(@RequestParam Long id) {
        Student student = new Student(10000001, "张三", 20, 1);
        redisUtil.set("set2Key",student);
        return redisUtil.get("set2Key");
    }

    //get请求
    @RequestMapping(value = "/get1", method = RequestMethod.GET)
    public Object get1(@RequestParam Long id) {
        return redisUtil.get("set1Key");
    }

    //get请求
    @RequestMapping(value = "/get2", method = RequestMethod.GET)
    public Object get2(@RequestParam Long id) {
        return redisUtil.get("set2Key");
        //redisUtil.<Student> get("set2Key",new TypeReference<>());
    }

}
