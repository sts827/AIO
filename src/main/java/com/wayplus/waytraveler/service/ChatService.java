package com.wayplus.waytraveler.service;

import com.wayplus.waytraveler.mapper.ChatMapper;
import com.wayplus.waytraveler.mapper.UserMapper;
import com.wayplus.waytraveler.model.ChatMessage;
import com.wayplus.waytraveler.model.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ChatService {

    private final UserMapper userMapper;

    private final ChatMapper chatMapper;

    @Autowired
    public ChatService(UserMapper userMapper,ChatMapper chatMapper){
        this.userMapper=userMapper;
        this.chatMapper = chatMapper;
    }

    public void createRoom(ChatRoom chatRoom){
        // 방을 생성
        chatMapper.insertChatRoom(chatRoom);

    }

    public ArrayList<ChatRoom> getRoomList(){
        ArrayList<ChatRoom> rooms = chatMapper.selectChatRoomList();
        return rooms;
    }

//    public ChatMessage joinRoom(int roomId){
//
//        ChatMessage messageContent = chatMapper.selectChatMessagesByRoomID(roomId);
//
//        return messageContent;
//    }


    public ArrayList<ChatMessage> selectChatMessageByRoomId(int id) {
        ArrayList<ChatMessage> messages = chatMapper.selectChatMessagesByRoomID(id);
        return messages;
    }
}
