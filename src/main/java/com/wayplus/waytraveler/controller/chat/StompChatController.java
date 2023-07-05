package com.wayplus.waytraveler.controller.chat;

import com.wayplus.waytraveler.model.ChatMessage;
import com.wayplus.waytraveler.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StompChatController {

    private final Logger logger = LoggerFactory.getLogger(StompChatController.class);

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public StompChatController(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    // @MessageMapping 을 통해 WebSocket으로 들어오는 메세지 발행을 처리
    @MessageMapping("/chat/enter")
    public void enterRoom(ChatMessage message,String roomType){
        if (roomType.equals("private")){
            message.setMessage(message.getUser_id()+"님이 입장하였습니다.");
            messagingTemplate.convertAndSend("/sub/chat/room"+message.getRoom_id(),message);
        }else if(roomType.equals("group")){
            message.setMessage(message.getUser_id()+"님이 참가하였습니다.");
            messagingTemplate.convertAndSend("/sub/chat/room"+message.getRoom_id(),message);
        }
    }

    @MessageMapping(value="/chat/message")
    public void message(ChatMessage message,String roomType){
        if (roomType.equals("private")){
            messagingTemplate.convertAndSend("/sub/chat/room"+ message.getRoom_id(),message);
        }else if(roomType.equals("group")){
            messagingTemplate.convertAndSend("/sub/chat/room"+message.getRoom_id(),message);
        }
    }

//    @MessageMapping("/app/privateChat")
//    public void sendPrivateMessage(ChatMessage message, LoginUser loginUser) {
//        logger.debug("message: "+message);
//        // 수신자에게 개별 채팅 메시지 전송
//        messagingTemplate.convertAndSendToUser(message.getUser_id(), "/private/messages", message);
//    }
//
//    @MessageMapping("/app/groupChat")
//    public void sendGroupMessage(ChatMessage message) {
//        // 채팅 방에 속한 모든 클라이언트에게 그룹 채팅 메시지 전송
//        messagingTemplate.convertAndSend("/group/messages", message);
//    }

}
