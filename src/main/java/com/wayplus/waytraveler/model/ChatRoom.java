package com.wayplus.waytraveler.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
    private String room_id;
    private String room_name;
    private String create_date;
    private Set<WebSocketSession> sessions = new HashSet<>();
    // webSocketSession은 spring에서 webSocket Connection이 맺어진 세션

    public static ChatRoom create(String room_name){
        ChatRoom room = new ChatRoom();
        room.room_id = UUID.randomUUID().toString();
        room.room_name=room_name;
        return room;
    }
}
