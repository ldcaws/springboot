package com.ldc.transactionaldemo.controller;

import com.ldc.transactionaldemo.model.User;
import com.ldc.transactionaldemo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/13 22:12
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@RequestBody User user) throws Exception {
        demoService.save(user);
        return "success";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(@RequestBody User user) throws Exception {
        demoService.create(user);
        return "success";
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getList() throws Exception {
        return demoService.getList();
    }

}
