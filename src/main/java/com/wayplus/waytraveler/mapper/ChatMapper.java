package com.wayplus.waytraveler.mapper;

import com.wayplus.waytraveler.model.ChatMessage;
import com.wayplus.waytraveler.model.ChatRoom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
@Repository
public interface ChatMapper {

    void insertChatRoom(ChatRoom chatRoom);

    // enter
    ChatRoom selectChatRoom(int roomID);

    // default: list
    ArrayList<ChatRoom> selectChatRoomList();

    // search list
    ArrayList<ChatRoom> selectChatRoomListBySearch(HashMap<String,Object> params);

    void insertChatMessage(ChatMessage chatMessage);

    // admin
    ChatMessage selectChatMessagesByRoomID(int roomId);

}
