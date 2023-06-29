package com.wayplus.waytraveler.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserSession {
    private int seq;
    private String user_email;
    private String login_session;
    private String login_ip;
    private String login_agent;
    private String login_referer;
    private String login_time;
    private String logout_time;
    private String logout_type;
}
