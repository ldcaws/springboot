package com.ldc.synchronizeddemo.controller;

import com.ldc.synchronizeddemo.common.IdempotentUtil;
import org.apache.commons.collections4.map.LRUMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/29 20:48
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    // 缓存 ID 集合
    private Map<String, Integer> reqCache = new HashMap<>();

    /**
     * 被重复请求的方法
     */
    @RequestMapping("/add")
    public String addUser(String id) {
        // 业务代码...
        System.out.println("添加用户ID:" + id);
        return "执行成功！";
    }



    @RequestMapping("/add2")
    public String addUser2(String id) {
        // 非空判断(忽略)...
        synchronized (this.getClass()) {
            // 重复请求判断
            if (reqCache.containsKey(id)) {
                // 重复请求
                System.out.println("请勿重复提交！！！" + id);
                return "执行失败";
            }
            // 存储请求 ID
            reqCache.put(id, 1);
        }
        // 业务代码...
        System.out.println("添加用户ID:" + id);
        return "执行成功！";
    }

    private static String[] reqCache2 = new String[100]; // 请求 ID 存储集合
    private static Integer reqCacheCounter2 = 0; // 请求计数器（指示 ID 存储的位置）

    @RequestMapping("/add3")
    public String addUser3(String id) {
        // 非空判断(忽略)...
        synchronized (this.getClass()) {
            // 重复请求判断
            if (Arrays.asList(reqCache2).contains(id)) {
                // 重复请求
                System.out.println("请勿重复提交！！！" + id);
                return "执行失败";
            }
            // 记录请求 ID
            if (reqCacheCounter2 >= reqCache2.length) reqCacheCounter2 = 0; // 重置计数器
            reqCache2[reqCacheCounter2] = id; // 将 ID 保存到缓存
            reqCacheCounter2++; // 下标往后移一位
        }
        // 业务代码...
        System.out.println("添加用户ID:" + id);
        return "执行成功！";
    }

    // 最大容量 100 个，根据 LRU 算法淘汰数据的 Map 集合
    private LRUMap<String, Integer> reqCache3 = new LRUMap<>(100);

    @RequestMapping("/add4")
    public String addUser4(String id) {
        // 非空判断(忽略)...
        synchronized (this.getClass()) {
            // 重复请求判断
            if (reqCache3.containsKey(id)) {
                // 重复请求
                System.out.println("请勿重复提交！！！" + id);
                return "执行失败";
            }
            // 存储请求 ID
            reqCache3.put(id, 1);
        }
        // 业务代码...
        System.out.println("添加用户ID:" + id);
        return "执行成功！";
    }

    @RequestMapping("/add5")
    public String addUser5(String id) {
        // 非空判断(忽略)...
        // -------------- 幂等性调用（开始） --------------
        if (!IdempotentUtil.judge(id, this.getClass())) {
            return "执行失败";
        }
        // -------------- 幂等性调用（结束） --------------
        // 业务代码...
        System.out.println("添加用户ID:" + id);
        return "执行成功！";
    }

}
