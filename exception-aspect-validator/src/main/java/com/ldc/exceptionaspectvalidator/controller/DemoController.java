package com.ldc.exceptionaspectvalidator.controller;

import com.ldc.exceptionaspectvalidator.common.exception.GlobalParamException;
import com.ldc.exceptionaspectvalidator.common.response.ResponseData;
import com.ldc.exceptionaspectvalidator.common.validator.ValidatorUtil;
import com.ldc.exceptionaspectvalidator.model.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/3 8:18
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    //post请求
    @RequestMapping(value = "/getList1", method = RequestMethod.POST)
    public Object getList1(@RequestBody Map<String,String> map) {
        //Long id = map.get("id");//可以同时传不同类型参数，需要强转
        String name = map.get("name");
        return name;
    }

    //post请求
    @RequestMapping(value = "/getList2", method = RequestMethod.POST)
    public Object getList2(@RequestBody Map<String,Long> map) {
        Long id = map.get("id");
        //String name = map.get("name");//不能同时传string参数，可以同时传同类型的参数
        return id;
    }

    //post请求
    @RequestMapping(value = "/insert1", method = RequestMethod.POST)
    public ResponseData insert1(@RequestBody @Validated User user) throws GlobalParamException {//@Validated此注解校验未进入方法
        Long id = user.getId();//可以接受不同类型参数，不需要强转
        String name = user.getName();
        Integer age = user.getAge();
        Date time = user.getTime();
        //if (true) throw new GlobalParamException("测试参数异常抛出");
        return ResponseData.success(user);
    }

    //post请求
    @RequestMapping(value = "/insert2", method = RequestMethod.POST)
    public Object insert2(@RequestBody  User user) throws GlobalParamException {
        ValidatorUtil.validate(user);
        Long id = user.getId();//可以接受不同类型参数，不需要强转
        String name = user.getName();
        Integer age = user.getAge();
        Date time = user.getTime();
        if (true) throw new GlobalParamException("测试参数异常抛出");
        return ResponseData.success(user);
    }

    //get请求
    @RequestMapping(value = "/get1", method = RequestMethod.GET)
    public Object get1(@RequestParam Long id,@RequestParam String name) {
        return id;
    }

    //get请求
    @RequestMapping(value = "/get2", method = RequestMethod.GET)
    public Object get2(@RequestParam Long id,@RequestParam String name) {
        return name;
    }

}
