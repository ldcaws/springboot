package com.ldc.annotationdemo.controller;

import com.ldc.annotationdemo.common.annotion.Log;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/15 22:37
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    //post请求
    @Log(content = "任务管理-任务列表查询", eventType = "1", operationType = "2", eventLevel = "3")
    @RequestMapping(value = "/getList1", method = RequestMethod.POST)
    public Object getList1(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        return name;
    }
}