package com.ldc.sftpdemo.controller;

import com.ldc.sftpdemo.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/13 20:42
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    private Logger log = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Object uploadPath(@RequestParam("fileContent") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        String fileId = "File" + DateUtil.formateNowByPattern(DateUtil.DATE_FROMMAT_YMDHMS) + Constants.DOT + ext;
        String storeDirectory = SFTPUtil.upload(file.getInputStream(), fileId, Constants.PATH);
        return storeDirectory + Constants.SLASH + fileId;
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public Object downloadAirspace(HttpServletRequest request, HttpServletResponse response) {
        byte[] fileByte = null;
        fileByte = SFTPUtil.download("/usr/local/resource/picture/20200713", "File20200713214837.jpg");
        // 清空response
        response.reset();
        try {
            String realFileName = "111.jpg";
            FileUtil.downloadFile(request, response, fileByte, realFileName);
            return "下载成功";
        } catch (IOException e) {
            log.error(LogUtil.format("输出流处理异常", e));
            throw new RuntimeException("输出流处理异常");
        }
    }

}
