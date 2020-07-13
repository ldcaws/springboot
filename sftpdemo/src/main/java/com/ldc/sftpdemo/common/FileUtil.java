package com.ldc.sftpdemo.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/13 20:48
 */
public class FileUtil {

    /**
     * Description: 文件下载
     * @author ldc
     * @date 2020-06-28 18:24:52
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String finalPath, String realFileName) throws IOException {
        OutputStream out = null;
        FileInputStream in = null;
        try {
            //得到要下载的文件
            File file = new File(finalPath);
            //如果文件不存在
            if (!file.exists()) {
                request.setAttribute("message", "您要下载的资源已被删除！！");
            }
            //设置响应头，控制浏览器下载该文件
            //返回客户端浏览器的版本号、类型
            String agent = request.getHeader("USER-AGENT");
            try {
                //针对IE或者以IE为内核的浏览器：
                if (agent.contains("MSIE") || agent.contains("Trident")) {
                    realFileName = java.net.URLEncoder.encode(realFileName, "UTF-8");
                } else {
                    //非IE浏览器的处理：
                    realFileName = new String(realFileName.getBytes("UTF-8"), "ISO-8859-1");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setHeader("content-disposition", "attachment;filename=" + realFileName);

            //读取要下载的文件，保存到文件输入流
            in = new FileInputStream(finalPath);
            //创建输出流
            out = response.getOutputStream();
            //创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            //循环将输入流中的内容读取到缓冲区当中
            while ((len = in.read(buffer)) > 0) {
                //输出缓冲区的内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
            //关闭文件输入流
            in.close();
            //关闭输出流
            out.close();

        } catch (Exception e) {
        } finally {
            //关闭文件输入流
            if (in != null) {
                in.close();
            }
            //关闭输出流
            if (out != null) {
                out.close();
            }
        }
    }

    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, byte[] fileByte, String realFileName) throws IOException {
        OutputStream outputStream = null;
        try {
            //设置响应头，控制浏览器下载该文件
            //返回客户端浏览器的版本号、类型
            String agent = request.getHeader("USER-AGENT");
            try {
                //针对IE或者以IE为内核的浏览器：
                if (agent.contains("MSIE") || agent.contains("Trident")) {
                    realFileName = java.net.URLEncoder.encode(realFileName, "UTF-8");
                } else {
                    //非IE浏览器的处理：
                    realFileName = new String(realFileName.getBytes("UTF-8"), "ISO-8859-1");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setHeader("content-disposition", "attachment;filename=" + realFileName);
            response.setContentType("application/x-msdownload");
            outputStream = response.getOutputStream();
            outputStream.write(fileByte);
            outputStream.flush();
            //关闭输出流
            outputStream.close();

        } catch (Exception e) {
        } finally {
            //关闭输出流
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

}
