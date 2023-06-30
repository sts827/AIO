package com.wayplus.waytraveler.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private int message_id; //pk
    private int room_id; //fk
    private String user_id; //fk
    private String message;
    private String create_date;

}
