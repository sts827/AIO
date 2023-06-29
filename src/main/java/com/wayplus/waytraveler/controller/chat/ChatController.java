package com.wayplus.waytraveler.controller.chat;

import com.wayplus.waytraveler.model.ChatMessage;
import com.wayplus.waytraveler.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/privateChat")
    public void sendPrivateMessage(ChatMessage message, LoginUser loginUser) {
        // 수신자에게 개별 채팅 메시지 전송
        messagingTemplate.convertAndSendToUser(message.getUser_id(), "/private/messages", message);
    }

    @MessageMapping("/groupChat")
    public void sendGroupMessage(ChatMessage message) {
        // 채팅 방에 속한 모든 클라이언트에게 그룹 채팅 메시지 전송
        messagingTemplate.convertAndSend("/group/messages", message);
    }

}
