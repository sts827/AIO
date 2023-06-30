package com.wayplus.waytraveler.controller.chat;

import com.wayplus.waytraveler.model.ChatMessage;
import com.wayplus.waytraveler.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatController {
    private final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/app/privateChat")
    public void sendPrivateMessage(ChatMessage message, LoginUser loginUser) {

        logger.debug("message: "+message);
        // 수신자에게 개별 채팅 메시지 전송
        messagingTemplate.convertAndSendToUser(message.getUser_id(), "/private/messages", message);
    }

    @MessageMapping("/app/groupChat")
    public void sendGroupMessage(ChatMessage message) {
        // 채팅 방에 속한 모든 클라이언트에게 그룹 채팅 메시지 전송
        messagingTemplate.convertAndSend("/group/messages", message);
    }

}
