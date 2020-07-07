package com.ldc.freemarkdoc.common;

import java.io.File;
import java.io.IOException;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/7 22:31
 */
public class FileTest {

    public static void main(String[] args) throws IOException {
        File f1 = new File("E://aaa//bbb");
        if (!f1.exists()) {
            f1.mkdirs();
        }
        // f1.mkdirs(); 生成所有目录
        // f1.mkdir(); 必须AAA目录存在才能生成BBB目录

        File f2 = new File("E://aaa//bbb//c.txt");
        if (!f2.exists()) {
            // 不能生成目录，只能创建文件，且/aaa/bbb目录必须存在
            f2.createNewFile();
        }
    }

}
