package com.ldc.designpattern.factory.po;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 21:51
 */
public class Noodle extends Food {

    private String noodle;
    private String salt;

    public String getNoodle() {
        return noodle;
    }

    public void setNoodle(String noodle) {
        this.noodle = noodle;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
