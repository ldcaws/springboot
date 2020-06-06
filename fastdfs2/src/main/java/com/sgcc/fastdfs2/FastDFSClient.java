package com.sgcc.fastdfs2;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 
 * @author ldc
 * 
 * @date: 2020年5月29日 上午11:32:08
 *
 * @Description: fastdfs工具类
 *
 */
public class FastDFSClient {
    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    static
    {
        try
        {
            String g_connect_timeout = FastDFSConfigurer.getFastDFSConfigurer().getConnect_timeout();
            if ((g_connect_timeout != null) && (g_connect_timeout.trim().length() != 0)) {
                ClientGlobal.setG_connect_timeout(Integer.parseInt(g_connect_timeout.trim()) * 1000);
            }
            String g_network_timeout = FastDFSConfigurer.getFastDFSConfigurer().getNetwork_timeout();
            if ((g_network_timeout != null) && (g_network_timeout.trim().length() != 0)) {
                ClientGlobal.setG_network_timeout(Integer.parseInt(g_network_timeout.trim()) * 1000);
            }
            String g_charset = FastDFSConfigurer.getFastDFSConfigurer().getCharset();
            if ((g_charset != null) && (g_charset.trim().length() != 0)) {
                ClientGlobal.setG_charset(g_charset.trim());
            }
            String trackerServers = FastDFSConfigurer.getFastDFSConfigurer().getTracker_server();
            ClientGlobal.initByTrackers(trackerServers.trim());
            String g_tracker_http_port = FastDFSConfigurer.getFastDFSConfigurer().getTracker_http_port();
            if ((g_tracker_http_port != null) && (g_tracker_http_port.trim().length() != 0)) {
                ClientGlobal.setG_tracker_http_port(Integer.parseInt(g_tracker_http_port));
            }
            String g_anti_steal_token = FastDFSConfigurer.getFastDFSConfigurer().getAnti_steal_token();
            if ((g_anti_steal_token != null) && (g_anti_steal_token.trim().length() != 0)) {
                ClientGlobal.setG_anti_steal_token(Boolean.parseBoolean(g_anti_steal_token));
            }
        }
        catch (IOException ex)
        {
            logger.error("FastDFS Client Init Fail! IOException", ex);
            ex.printStackTrace();
        }
        catch (MyException ex)
        {
            logger.error("FastDFS Client Init Fail!MyException", ex);
            ex.printStackTrace();
        }
    }

    public static String[] upload(FastDFSFile file) {
        logger.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);

        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", file.getAuthor());

        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        StorageClient storageClient = null;
        try {
            storageClient = getTrackerClient();
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (IOException e) {
            logger.error("IO Exception when uploadind the file:" + file.getName(), e);
        } catch (Exception e) {
            logger.error("Non IO Exception when uploadind the file:" + file.getName(), e);
        }
        logger.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");

        if (uploadResults == null && storageClient != null) {
            logger.error("upload file fail, error code:" + storageClient.getErrorCode());
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        logger.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
        return uploadResults;
    }

    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            InputStream ins = new ByteArrayInputStream(fileByte);
            return ins;
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    public static int deleteFile(String groupName, String remoteFileName)
            throws Exception {
        StorageClient storageClient = getTrackerClient();
        int i = storageClient.delete_file(groupName, remoteFileName);
        logger.info("delete file successfully!!!" + i);
        logger.info(i == 0 ? "删除文件成功" : "删除文件失败:" + i);
        return i;
    }

    public static StorageServer[] getStoreStorages(String groupName)
            throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getStoreStorages(trackerServer, groupName);
    }

    public static org.csource.fastdfs.ServerInfo[] getFetchStorages(String groupName,
                                                String remoteFileName) throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    public static String getTrackerUrl() throws IOException {
        return "http://" + getTrackerServer().getInetSocketAddress().getHostString() + ":" + ClientGlobal.getG_tracker_http_port() + "/";
    }

    private static StorageClient getTrackerClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }

    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

}
