package com.ldc.poiexcel.model;

import java.io.Serializable;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/22 21:04
 */
public class Student implements Serializable {

    private long id;
    private String name;
    private int age;
    private int sex;

    /**
     * @param id
     * @param name
     * @param age
     * @param sex
     */
    public Student(long id, String name, int age, int sex) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
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
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }

}
