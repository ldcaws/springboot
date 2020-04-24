package com.ldc.springsecuritygithub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: ss
 * @time: 2020/4/24 23:21
 */
@RestController
public class LoginController {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/authorization_code")
    public String authorization_code(String code) throws JsonProcessingException {
        Map<String,String> map = new HashMap<>();
        map.put("client_id","3603532808869d5b4355");
        map.put("client_secret", "9a6cfedd4a36559da8d4ae8e1af0e3a2cf51ef3d");
        map.put("state", "ldcaws");
        map.put("code", code);
        map.put("redirect_uri", "http://localhost:8080/authorization_code");
        Map<String,String> result = restTemplate.postForObject("https://github.com/login/oauth/access_token",map,Map.class);
        System.out.println(result);

        HttpHeaders httpheaders = new HttpHeaders();
        httpheaders.add("Authorization", "token " + result.get("access_token"));
        HttpEntity<?> httpEntity = new HttpEntity<>(httpheaders);
        ResponseEntity<Map> exchange = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, httpEntity, Map.class);
        System.out.println("exchange.getBody() = " + new ObjectMapper().writeValueAsString(exchange.getBody()));

        return exchange.getBody().toString();
    }

}
