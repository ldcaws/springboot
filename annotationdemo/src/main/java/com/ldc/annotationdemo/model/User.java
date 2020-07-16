package com.ldc.annotationdemo.model;

import com.ldc.annotationdemo.common.validation.CheckTimeInterval;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.util.Date;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/15 22:20
 */
@CheckTimeInterval(startTime = "startTime",endTime = "endTime",message = "开始时间不能大于结束时间")
public class User {

    private String name;

    private String password;

    @FutureOrPresent(message = "计划开始时间必须是将来时间")
    private Date startTime;

    @Future(message = "计划结束时间必须是将来时间")
    private Date endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
