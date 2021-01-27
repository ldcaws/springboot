package com.ldc.websocket.handler;

import com.alibaba.fastjson.JSON;
import com.ldc.websocket.common.SpringBeanUtil;
import com.ldc.websocket.model.WebsocketMessage;
import com.ldc.websocket.controller.AppWebSocketServer;
import com.ldc.websocket.controller.PageWebSocketServer;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/10 21:48
 */
public class UavMessageHandler implements IMessageHandler {

    private AppWebSocketServer appWebSocketServer;

    private PageWebSocketServer pageWebSocketServer;

    @Override
    public boolean handlerMessage(WebsocketMessage websocketMessage) {

        if (websocketMessage == null) return false;
        Object jsonData = websocketMessage.getMessage();

        appWebSocketServer = SpringBeanUtil.getBean(AppWebSocketServer.class);
        pageWebSocketServer = SpringBeanUtil.getBean(PageWebSocketServer.class);

        ConcurrentHashMap<String, Session> sendUsers = null;
        if (jsonData != null) {
            sendUsers = appWebSocketServer.getRecievers(websocketMessage.getUserId(), websocketMessage.getToken(), appWebSocketServer.getUserList());
            /*if (sendUsers != null && sendUsers.size()!=0) {
                Set<String> keySet = sendUsers.keySet();
                for (String wsKey : keySet) {
                    uavWebSocketServer.sendMessageToUser(wsKey, JSON.toJSONString(websocketMessage));
                }
                return true;
            }*/
            appWebSocketServer.sendMessageToUser("1@@2", JSON.toJSONString(websocketMessage));
            pageWebSocketServer.sendMessageToUser("1@@2", JSON.toJSONString(websocketMessage));
        }

        return false;
    }

}
