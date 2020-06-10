package com.ldc.websocket.handler;

import com.ldc.websocket.model.WebsocketMessage;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/10 21:43
 */
public interface IMessageHandler {

    boolean handlerMessage(WebsocketMessage websocketMessage);

}