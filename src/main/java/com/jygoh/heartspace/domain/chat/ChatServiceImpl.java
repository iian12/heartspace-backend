package com.jygoh.heartspace.domain.chat;

import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final WebSocketSessionManager webSocketSessionManager;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatServiceImpl(UserRepository userRepository, ChatRoomRepository chatRoomRepository,
        WebSocketSessionManager webSocketSessionManager,
        SimpMessagingTemplate simpMessagingTemplate) {
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.webSocketSessionManager = webSocketSessionManager;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendMessage(String content, String profileId, String token) {
        Long userId = TokenUtils.getUserIdFromToken(token);
        Users receiver = userRepository.findByProfileId(profileId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findChatRoom(userId, receiver.getId());

        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            String receiverSessionId = webSocketSessionManager.getOtherPrincipal(receiver.getId());

            ChatMessage chatMessage = ChatMessage.builder()
                .senderId(userId)
                .roomId(chatRoom.getId())
                .content(content)
                .build();

            simpMessagingTemplate.convertAndSend(receiverSessionId + "/queue/chat", content);
        } else {
            ChatRoom chatRoom = ChatRoom.builder()
                .user1Id(userId)
                .user2Id(receiver.getId())
                .build();
            chatRoomRepository.save(chatRoom);
            simpMessagingTemplate.convertAndSend(receiver + "/queue/chat", content);
        }
    }
}
