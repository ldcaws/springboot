package com.ldc.websocket.handler;

import com.alibaba.fastjson.JSON;
import com.ldc.websocket.common.SpringBeanUtil;
import com.ldc.websocket.model.WebsocketMessage;
import com.ldc.websocket.controller.PageWebSocketServer;

import javax.websocket.Session;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/10 22:48
 */
public class RTMessageHandler implements IMessageHandler {

    private PageWebSocketServer pageWebSocketServer;

    @Override
    public boolean handlerMessage(WebsocketMessage websocketMessage) {

        if (websocketMessage == null) return false;
        pageWebSocketServer = SpringBeanUtil.getBean(PageWebSocketServer.class);
        System.out.print(pageWebSocketServer);

        Object jsonData = websocketMessage.getMessage();
        //UAVLocation location = (UAVLocation) JSONUtil.JSONToObject(JSONUtil.object2JSONStr(jsonData), UAVLocation.class, new HashMap());
        //boolean saveFlag = uavService.save(location);
        if ("saveFlag".isEmpty()) {
            /*
             * app端 巡检作业人员 回传无人机实时数据，server端接收到数据，推送给:
             * 1、推送给在线状态的web用户
             * 2、推送给与登录app端用户属于同一班组的所有用户
             * */
            ConcurrentHashMap<String, Session> sendUsers = pageWebSocketServer.getRecievers(websocketMessage.getUserId(), websocketMessage.getToken(), pageWebSocketServer.getUserList());
            if (sendUsers != null && sendUsers.size() != 0) {
                Set<String> keySet = sendUsers.keySet();
                for (String wsKey : keySet) {
                    pageWebSocketServer.sendMessageToUser(wsKey, JSON.toJSONString(websocketMessage));
                }
                return true;
            }
        }

        return false;
    }

}
