package com.wayplus.waytraveler.service;


import com.wayplus.waytraveler.mapper.PageMapper;
import com.wayplus.waytraveler.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService {

    private final PageMapper pageMapper;
    private final UserMapper userMapper;

    @Autowired
    public PageService(PageMapper pageMapper,UserMapper userMapper){
        this.pageMapper=pageMapper;
        this.userMapper=userMapper;
    }

}
