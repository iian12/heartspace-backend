package com.jygoh.heartspace.domain.chat;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
public class WebSocketTestClient {

    private StompSession stompSession;
    private WebSocketStompClient stompClient;

    public WebSocketTestClient() {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
    }

    // WebSocket 서버 연결
    public void connect(String url) {
        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                stompSession = session;
            }
        };
        stompClient.connectAsync(url, sessionHandler);
    }

    // WebSocket을 통해 메시지 보내기
    public void sendMessage(String destination, Object message) {
        stompSession.send(destination, message);
    }

    // WebSocket을 통해 메시지 수신하기
    public void receiveMessage(String destination, StompFrameHandler frameHandler) {
        stompSession.subscribe(destination, frameHandler);
    }
}
