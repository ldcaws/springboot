package com.ldc.websocket.processor;

import com.ldc.websocket.handler.IMessageHandler;
import com.ldc.websocket.model.WebsocketMessage;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/10 21:42
 */
public class MessageProcessor {

    private IMessageHandler messageHandler;

    public MessageProcessor(){
    }

    public MessageProcessor(IMessageHandler messageHandler){
        this.messageHandler=messageHandler;
    }

    public boolean handlerMessage(WebsocketMessage message){
        return messageHandler.handlerMessage(message);
    }

}
