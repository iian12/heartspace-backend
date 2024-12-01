package com.jygoh.heartspace;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.jygoh.heartspace.domain.chat.ChatMessage;
import com.jygoh.heartspace.domain.chat.ChatService;
import com.jygoh.heartspace.domain.chat.WebSocketTestClient;
import com.jygoh.heartspace.domain.user.model.Users;
import java.lang.reflect.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MessageTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private WebSocketTestClient webSocketTestClient;

    private String user1Token;
    private String user2ProfileId;

    private Users user1;
    private Users user2;

    @BeforeEach
    public void setUp() {
        Users user1 = Users.builder()
            .email("test@example.com")
            .nickname("신비")
            .profileId("min")
            .build();

        Users user2 = Users.builder()
            .email("email@example.com")
            .nickname("민찬")
            .profileId("koala")
            .build();
    }

    @Test
    public void testSendMessageToChatRoom() throws Exception {
        webSocketTestClient.connect("ws://localhost:8080/ws");

        Thread.sleep(2000);

        String messageContent = "Hello";
        ChatMessage message = ChatMessage.builder()
            .content(messageContent).build();
        webSocketTestClient.sendMessage("/chat/send/koala", messageContent);
        StompFrameHandler handler = new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                ChatMessage receivedMessage = (ChatMessage) payload;
                assertNotNull(receivedMessage);
            }
        };

    }
}
