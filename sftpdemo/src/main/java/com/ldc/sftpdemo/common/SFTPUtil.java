package com.ldc.sftpdemo.common;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/9 22:30
 */
public class SFTPUtil {

    private static final Logger log = LoggerFactory.getLogger(SFTPUtil.class);

    private ChannelSftp sftp;

    private Session session;
    // FTP 登录用户名
    private String username= Constants.SFTP_USERNAME;
    // FTP 登录密码
    private String password= Constants.SFTP_PASSWORD;
    // FTP 服务器地址IP地址
    private String host = Constants.SFTP_HOST;
    //FTP 端口
    private int port = Constants.SFTP_PORT;

    /**
     * 连接sftp服务器
     */
    private void login(){
        try {
            JSch jsch = new JSch();
            log.info("sftp connect by host:{} username:{}",host,username);
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp)channel;
        } catch (JSchException e) {
            log.error("Cannot connect to specified sftp server : {}:{} \n Exception message is: {}", new Object[]{host, port, e.getMessage()});
        }
    }

    /**
     * 关闭连接 server
     */
    private void logout(){
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
                log.info("sftp is closed already");
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
                log.info("sshSession is closed already");
            }
        }
    }

    /**
     * 将输入流的数据上传到sftp作为文件
     */
    private void upload(String directory, String sftpFileName, InputStream input) throws SftpException{
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            log.warn("directory is not exist");
            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        sftp.put(input, sftpFileName);
        log.info("file:{} is upload successful" , sftpFileName);
    }

    /**
     * 上传单个文件
     */
    private void upload(String directory, String uploadFile) throws FileNotFoundException, SftpException{
        File file = new File(uploadFile);
        upload(directory, file.getName(), new FileInputStream(file));
    }

    /**
     * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。
     */
    private void upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException{
        upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));
    }

    /**
     * 将字符串按照指定的字符编码上传到sftp
     */
    private void upload(String directory, String sftpFileName, String dataStr, String charsetName) throws UnsupportedEncodingException, SftpException{
        upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));
    }

    /**
     * 下载文件
     */
    private void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException{
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
        log.info("file:{} is download successful" , downloadFile);
    }
    /**
     * 下载文件
     */
    private byte[] fileByte(String directory, String downloadFile) throws SftpException, IOException{
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);
        byte[] fileData = IOUtils.toByteArray(is);
        log.info("file:{} is download successful" , downloadFile);
        return fileData;
    }

    /**
     * 删除文件
     */
    private void remove(String directory, String deleteFile) throws SftpException {
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     *
     * Description: 创建目录
     * @author yangbao
     * @date 2020-07-09 13:57:24
     */
    private void mkdir(String directory) throws SftpException{
        String[] paths = directory.split(Constants.SLASH);
        String currentPath = Constants.EMPTY;
        for (String path : paths) {
            currentPath +=Constants.SLASH+path;
            if(!isExist(currentPath)){
                sftp.mkdir(path);
            }
            sftp.cd(currentPath);
        }
    }

    /**
     *
     * Description: 判断当前目录是否存在
     * @author yangbao
     * @date 2020-07-09 13:58:44
     */
    private boolean isExist(String path){
        try {
            sftp.stat(path);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     *
     * Description: 上传文件
     * @author yangbao
     * @date 2020-07-07 18:37:03
     */
    public static String upload(InputStream in,String fileName,String rootPath){
        SFTPUtil sftp = new SFTPUtil();
        String storeDirectory = rootPath + DateUtil.formatDateByPattern(new Date(), DateUtil.DATE_FROMMAT_YMD);
        try {
            sftp.login();//连接登录服务器
            sftp.mkdir(storeDirectory);
            sftp.upload(storeDirectory, fileName, in);
        } catch (Exception e) {
            log.error(LogUtil.format("上传文件异常",e));
            throw new RuntimeException("上传文件异常");
        }finally {
            if(sftp != null){
                sftp.logout();
            }
        }
        return storeDirectory;
    }

    /**
     *
     * Description: 下载文件，获取字节码
     * @author yangbao
     * @date 2020-07-07 18:37:03
     */
    public static byte[] download(String directory,String fileName){
        SFTPUtil sftp = new SFTPUtil();
        try {
            sftp.login();//连接登录服务器
            return sftp.fileByte(directory, fileName);
        } catch (Exception e) {
            log.error(LogUtil.format("下载文件异常",e));
            throw new RuntimeException("下载文件异常");
        }finally {
            if(sftp != null){
                sftp.logout();
            }
        }
    }

    /**
     *
     * Description: 删除文件
     * @author yangbao
     * @date 2020-07-07 18:45:02
     */
    public static void delete(String directory,String fileName){
        SFTPUtil sftp = new SFTPUtil();
        try {
            sftp.login();//连接登录服务器
            sftp.remove(directory, fileName);
        } catch (Exception e) {
            log.error(LogUtil.format("删除文件异常",e));
            throw new RuntimeException("删除文件异常");
        }finally {
            if(sftp != null){
                sftp.logout();
            }
        }
    }

}