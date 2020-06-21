package com.ldc.scheduledemo.server;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/21 10:42
 */
@Component
public class ScheduleService {

    //每分钟执行一次，*表示每，从右往左读
    @Scheduled(cron = "0 * * * * ?")
    public void cron() {
        System.out.println("cron:" + new Date());
    }
}
