package com.wayplus.waytraveler.service;

import com.wayplus.waytraveler.mapper.ChatRoomMapper;
import com.wayplus.waytraveler.model.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomMapper chatRoomMapper;

    public ArrayList<ChatRoom> selectChatRoomLists() {
        return chatRoomMapper.selectChatRoomLists();
    }

    public void createRoom(ChatRoom room) {
        chatRoomMapper.insertChatRoom(room);
    }

    public ChatRoom findByRoomName(String roomName) {
        return chatRoomMapper.selectChatRoomByName(roomName);
    }
}
