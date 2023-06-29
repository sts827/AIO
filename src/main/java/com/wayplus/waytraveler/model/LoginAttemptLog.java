package com.wayplus.waytraveler.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAttemptLog {
    private int log_id;
    private String user_email;
    private String attempt_ip;
    private String attempt_agent;
    private String attempt_referer;
    private String attempt_time;
    private String error_code;
    private String error_message;
}
