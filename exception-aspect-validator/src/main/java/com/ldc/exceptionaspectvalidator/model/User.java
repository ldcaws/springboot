package com.ldc.exceptionaspectvalidator.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/3 8:26
 */
public class User implements Serializable {

    public static final long serialVersionUID = 1L;

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @DecimalMin(value = "1")
    @DecimalMax(value = "20")
    private int age;
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
