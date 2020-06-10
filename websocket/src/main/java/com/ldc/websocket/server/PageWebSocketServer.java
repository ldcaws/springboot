package com.ldc.websocket.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ldc.websocket.handler.UavMessageHandler;
import com.ldc.websocket.model.WebsocketMessage;
import com.ldc.websocket.processor.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/10 20:46
 */
@ServerEndpoint("/websocket/web/{userId}/{token}")
@Component
public class PageWebSocketServer extends BaseWebSocketServer {

    private Logger logger = LoggerFactory.getLogger(PageWebSocketServer.class);

    private static ConcurrentHashMap<String, Session> userList = new ConcurrentHashMap<>();

    private MessageProcessor messageProcessor = null;

    public ConcurrentHashMap<String, Session> getUserList() {
        return userList;
    }

    /**
     * 连接成功后,会触发UI上onopen方法
     *
     * @param session
     * @throws Exception
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam("userId") String userId, @PathParam("token") String token) throws Exception {
        String wsKey = createKey(userId, token);
        if (userList.get(wsKey) == null) {
            userList.put(wsKey, session);
            addOnlineCount();
            logger.info("Websocket-WEB用户【" + userId + "】建立连接成功！");
        } else {
            logger.info("Websocket-WEB用户【" + userId + "】已经连接！");
        }
    }

    /**
     * 在UI在用js调用websocket.send()时候，会调用该方法,接收客户端消息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        logger.info("Websocket-收到客户端消息:" + message);
        //接受无人机的位置消息
        message = message.replaceAll("null","\"\"");
        //WebsocketMessage websocketMessage = (WebsocketMessage) JSONUtil.JSONToObject(message, WebsocketMessage.class, new HashMap());
        WebsocketMessage websocketMessage = JSON.parseObject(JSONObject.toJSONString(JSON.parse(message)),WebsocketMessage.class);
        if (websocketMessage != null && websocketMessage.getType() != null && websocketMessage.getType().trim().equals("type")) {
            messageProcessor = new MessageProcessor(new UavMessageHandler());
            messageProcessor.handlerMessage(websocketMessage);
        }
    }

    /**
     * 发生异常时调用此方法
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @OnError
    public void OnError(Session session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.info("Websocket-处理推送消息时出现异常：" + exception.getLocalizedMessage());
    }

    /**
     * 连接关闭后调用此方法
     *
     * @param token
     * @param userId
     * @throws Exception
     */
    @OnClose
    public void OnClose(@PathParam("userId") String userId, @PathParam("token") String token) throws Exception {
        logger.info("Websocket-【" + userId + "】已经关闭！");
        String wsKey = createKey(userId, token);
        if (userList.get(wsKey) != null) {
            userList.remove(wsKey);
            removeOnlineCount();
            logger.info("Websocket-用户【" + userId + "】已经移除！");
        }
    }


    /**
     * @param @param user_id
     * @param @param message
     * @return void
     * @Title: sendMessageToUser
     * @Description: 服务器主动给某个用户发送信息
     * @date createTime：2018年4月26日上午9:44:16
     */
    public void sendMessageToUser(String wsKey, String message) {
        Session session = userList.get(wsKey);
        if (wsKey != null && !"".equals(wsKey) && session != null) {
            if (session != null && session.isOpen()) {
                try {
                    System.out.print("推送给平台端【"+ wsKey+"】消息："+message);
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发送给同组织机构的所有在线用户
     *
     * @param websocketMessage
     */
    public void sendMessageToSameOrgUser(WebsocketMessage websocketMessage) {
        ConcurrentHashMap<String, Session> sendUsers = this.getRecievers(websocketMessage.getUserId(), websocketMessage.getToken(), this.getUserList());
        if (sendUsers != null && sendUsers.size() != 0) {
            Set<String> keySet = sendUsers.keySet();
            for (String wsKey : keySet) {
                this.sendMessageToUser(wsKey, JSON.toJSONString(websocketMessage));
            }
        }
    }

}
