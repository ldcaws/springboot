package com.ldc.requestdemo.controller;

import com.ldc.requestdemo.common.HttpRequestUtil;
import com.ldc.requestdemo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/28 19:37
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Resource
    private RestTemplate restTemplate;

    //测试get请求
    @RequestMapping(value = "/requestGet1", method = RequestMethod.GET)
    public Object requestGet1() {
        String result = restTemplate.getForObject("http://localhost:8080/rest/get1?id=1&name=zhangsan",String.class);
        return result;
    }

    //测试get请求
    @RequestMapping(value = "/requestGet2", method = RequestMethod.GET)
    public Object requestGet2() {
        String result = HttpRequestUtil.sendGet("http://localhost:8080/rest/get2","id=1&name=zhangsan");
        return result;
    }

    //测试post请求
    @RequestMapping(value = "/requestPost1", method = RequestMethod.GET)
    public Object requestPost1() {
        Map<String,String> map = new HashMap<>();
        map.put("name", "毛泽东");
        String result = restTemplate.postForObject("http://localhost:8080/rest/getList1",map,String.class);
        return result;
    }

    //测试post请求
    @RequestMapping(value = "/requestPost2", method = RequestMethod.GET)
    public Object requestPost2() {
        String result = HttpRequestUtil.sendPost("http://localhost:8080/rest/post1","name=111");
        return result;
    }

    //测试post请求
    @RequestMapping(value = "/requestPost3", method = RequestMethod.GET)
    public Object requestPost3() {
        User user = new User();
        user.setName("毛泽东");
        String result = restTemplate.postForObject("http://localhost:8080/rest/getList1", user, String.class);
        return result;
    }

    //测试post请求
    @RequestMapping(value = "/requestPost4", method = RequestMethod.GET)
    public Object requestPost4() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("name","毛泽东");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        String result = restTemplate.postForObject("http://localhost:8080/rest/post1",entity,String.class);
        return result;
    }

}
