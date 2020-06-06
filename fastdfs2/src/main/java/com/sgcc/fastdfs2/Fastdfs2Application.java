package com.sgcc.fastdfs2;

import org.csource.fastdfs.FileInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Fastdfs2Application {

    public static void main(String[] args) {
        SpringApplication.run(Fastdfs2Application.class, args);

        try {
            String url = FastDFSClient.getTrackerUrl();
            FileInfo fileInfo = FastDFSClient.getFile("group1", "M00/00/00/wKhYAl7O2GyATpEXAACIDAgYCYk069.jpg");
            System.out.println(fileInfo.toString() + "------------------" + fileInfo.getFileSize());
            FastDFSClient.downFile("group1", "M00/00/00/wKhYAl7O2GyATpEXAACIDAgYCYk069.jpg");
            System.out.print(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
