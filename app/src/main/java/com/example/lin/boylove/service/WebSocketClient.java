package com.example.lin.boylove.service;

import com.example.lin.boylove.entity.ChatSocket.MessageResponse;
import com.example.lin.boylove.entity.Response.ChatMessage;

import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

/**
 * Created by lin on 19/05/2018.
 */

public class WebSocketClient extends org.java_websocket.client.WebSocketClient {

    private WebSocketListener listener;

    public WebSocketClient(URI serverURI) {
        super(serverURI);
    }

    public WebSocketClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public WebSocketClient(URI serverUri, Draft draft, Map<String, String> headers, int connecttimeout) {
        super(serverUri, draft, headers, connecttimeout);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        final MessageResponse response = MessageResponse.fromJson(message);
        if (response.isWelcome()) {
            // process welcome here
            listener.onConnectSuccess();
        } else if (response.isPing()) {
            // process ping here
        } else { // message content
            listener.onMessageResponse(response.getMessageObject() );
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }

    public void setListener(WebSocketListener listener) {
        this.listener = listener;
    }

    public interface WebSocketListener {
        void onMessageResponse(ChatMessage message);

        void onConnectSuccess();
    }
}
