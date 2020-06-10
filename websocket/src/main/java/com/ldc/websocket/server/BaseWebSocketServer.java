package com.ldc.websocket.server;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/10 20:26
 */
public class BaseWebSocketServer {

    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    public static void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    public static void getOnlineCount() {
        onlineCount.get();
    }

    public static void removeOnlineCount() {
        onlineCount.decrementAndGet();
    }

    protected static String createKey(String userId, String token) {
        return userId + "@@" + token;
    }

    /**
     * 根据当前发送者id、token及所有在线人数，获取接收者列表
     *
     * @param senderUserId
     * @param senderToken
     * @param currentOnlineUsers
     * @return
     */
    public ConcurrentHashMap<String, Session> getRecievers(String senderUserId, String senderToken, ConcurrentHashMap<String, Session> currentOnlineUsers) {

        //根据发送者及当前在线人数，获取与当前发送数据用户属于同一班组的所有用户
        ConcurrentHashMap<String, Session> sendUsers = new ConcurrentHashMap();
        //sendUsers.put("userWsKey", currentOnlineUsers.get("userWsKey"));

        return sendUsers;
    }

}

