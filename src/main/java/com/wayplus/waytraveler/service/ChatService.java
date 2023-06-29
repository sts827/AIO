package com.wayplus.waytraveler.service;

import com.wayplus.waytraveler.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final UserMapper userMapper;

    @Autowired
    public ChatService(UserMapper userMapper){
        this.userMapper=userMapper;
    }

}
