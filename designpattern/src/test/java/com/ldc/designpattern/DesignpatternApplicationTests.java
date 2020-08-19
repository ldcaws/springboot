package com.ldc.designpattern;

import com.ldc.designpattern.demo.OrderService;
import com.ldc.designpattern.sale.SaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class DesignpatternApplicationTests {

    @Autowired
    private OrderService orderService;

//    @Test
//    void contextLoads() {
//        orderService.saveOrder();
//    }

//    @Test
//    void test() {
//        RedisTemplate redisTemplate = null;
//        redisTemplate.execute(new RedisCallback() {
//            @Override
//            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                return redisConnection.geoCommands();// 程序员该做的事
//            }
//        });
//    }

    @Autowired
    private SaleService saleService;

    @Test
    public void test() {
        double sale = saleService.sale("vip",100);
        System.out.println("当前用户的支付金额：" + sale);
    }

}
