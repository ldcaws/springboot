package com.ldc.designpattern.factory.po;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 21:50
 */
public class Chicken extends Food {

    private String chicken;
    private String spicy;
    private String salt;

    public String getChicken() {
        return chicken;
    }

    public void setChicken(String chicken) {
        this.chicken = chicken;
    }

    public String getSpicy() {
        return spicy;
    }

    public void setSpicy(String spicy) {
        this.spicy = spicy;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
