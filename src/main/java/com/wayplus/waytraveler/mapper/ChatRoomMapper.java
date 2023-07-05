package com.wayplus.waytraveler.mapper;

import com.wayplus.waytraveler.model.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ChatRoomMapper {

    ArrayList<ChatRoom> selectChatRoomLists();

    void insertChatRoom(ChatRoom room);

    ChatRoom selectChatRoomByName(String roomId);

}
