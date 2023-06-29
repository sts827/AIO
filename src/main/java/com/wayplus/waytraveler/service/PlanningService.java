package com.wayplus.waytraveler.service;


import com.wayplus.waytraveler.mapper.PlannerMapper;
import com.wayplus.waytraveler.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanningService {

    private final PlannerMapper plannerMapper;
    private final UserMapper userMapper;

    @Autowired
    public PlanningService(PlannerMapper plannerMapper,UserMapper userMapper){
        this.plannerMapper = plannerMapper;
        this.userMapper=userMapper;
    }
}
