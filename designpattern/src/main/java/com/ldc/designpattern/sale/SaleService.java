package com.ldc.designpattern.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: ss
 * @time: 2020/8/18 22:26
 */
@Service
public class SaleService {

//    @Autowired
//    private NormalStrategy normalStrategy;
//
//    @Autowired
//    private VipStrategy vipStrategy;

    Map<String,CalculateStrategy> calculateStrategyMap = new HashMap<>();

    public SaleService(List<CalculateStrategy> calculateStrategyList) {
        for (CalculateStrategy calculateStrategy : calculateStrategyList) {
            calculateStrategyMap.put(calculateStrategy.userType(),calculateStrategy);
        }
    }

    public double sale(String userType, double fee) {
//        if ("normal".equals(userType)) {
//            // todo:
//            //fee = fee*1.0;
//            normalStrategy.discount(fee);
//        } else if ("vip".equals(userType)) {
//            // todo:
//            //fee = fee*0.8;
//            vipStrategy.discount(fee);
//        }
//        return fee;

        CalculateStrategy strategy = calculateStrategyMap.get(userType);
        fee = strategy.discount(fee);
        return fee;
    }
}
