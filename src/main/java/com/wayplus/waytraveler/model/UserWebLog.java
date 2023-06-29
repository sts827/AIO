package com.wayplus.waytraveler.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWebLog {
    private int log_id;
    private String user_token;
    private String user_email;
    private String referer;
    private String request_uri;
    private String request_time;
    private String request_host;
    private String session_id;
    private String tracking;
}
