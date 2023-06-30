package com.wayplus.waytraveler.controller;


import com.wayplus.waytraveler.model.ChatRoom;
import com.wayplus.waytraveler.service.ChatService;
import com.wayplus.waytraveler.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class PageController {


    @Value("${cookie-set.domain}")
    private String cookieDomain;
    @Value("${cookie-set.prefix}")
    private String cookieName;
    @Value("${upload.path.file}")

    private String fileUploadPath;
    private final Logger logger = LoggerFactory.getLogger(PageController.class);
    private final PageService pageService;
    private final ChatService chatService;

    @Autowired
    public PageController(PageService pageService,ChatService chatService){
        this.pageService=pageService;
        this.chatService=chatService;
    }

    @GetMapping(value={"","/"})
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){

        ModelAndView mav = new ModelAndView();

        mav.setViewName("/index");

        return mav;
    }

    @GetMapping(value="/plans")
    public ModelAndView planningPage(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/plan/planning");
        return mav;
    }

    @GetMapping(value="/planning")
    public HashMap<String,Object> setPlanning(){
        HashMap<String,Object> resultMap = new HashMap<>();

        return resultMap;
    }

    @GetMapping("/rooms")
    public ModelAndView chatIndex(){
        return new ModelAndView("/chat/conversation");
    }

    @GetMapping("/api/getRooms")
    @ResponseBody
    public HashMap<String,Object> chatIndexPage(){

        HashMap<String,Object> result = new HashMap<>();

        try{
            ArrayList<ChatRoom> rooms = chatService.getRoomList();
            result.put("result",rooms);
            result.put("status","ok");

        }catch (Exception e){

        }
        return result;
    }


}
