package com.ldc.websocketclient.common;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/10 23:11
 */
public class JavaWebSocketClient extends WebSocketClient {

    private final Logger LOGGER = LoggerFactory.getLogger(JavaWebSocketClient.class);

    public JavaWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public JavaWebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LOGGER.info("Open a WebSocket connection on client. ");
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        LOGGER.info("Close a WebSocket connection on client. ");
    }

    @Override
    public void onMessage(String msg) {
        LOGGER.info("WebSocketClient receives a message: " + msg);
    }

    @Override
    public void onError(Exception exception) {
        LOGGER.error("WebSocketClient exception. ", exception);
    }

    public static void main(String[] args) {
        String serverUrl = "ws://127.0.0.1:8080/websocket/web/1/2";
        URI recognizeUri = null;
        try {
            recognizeUri = new URI(serverUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        WebSocketClient client = new JavaWebSocketClient(recognizeUri, new Draft_6455());
        client.connect();
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            System.out.println("还没有打开");
        }
        System.out.println("建立websocket连接");
        client.send("This is a message from client. ");
    }

}