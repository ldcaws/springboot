package com.sgcc.fastdfs;

import org.csource.fastdfs.FileInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class FastdfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastdfsApplication.class, args);

        try {
            String url = FastDFSClient.getTrackerUrl();
            FileInfo fileInfo = FastDFSClient.getFile("group1", "M00/00/00/wKgA7V7Q5qGAMLctAABNTZXabHc515.jpg");
            System.out.println(fileInfo.toString() + "------------------" + fileInfo.getFileSize());
            FastDFSClient.downFile("group1", "M00/00/00/wKgA7V7Q5qGAMLctAABNTZXabHc515.jpg");
            System.out.print(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
