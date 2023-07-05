package com.wayplus.waytraveler.controller.chat;

import com.wayplus.waytraveler.model.ChatMessage;
import com.wayplus.waytraveler.model.ChatRoom;
import com.wayplus.waytraveler.service.ChatRoomService;
import com.wayplus.waytraveler.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping(value="/chat")
public class RoomController {

    private final Logger logger = LoggerFactory.getLogger(RoomController.class);

    private ChatRoomService chatRoomService;
    private ChatService chatService;

    @Autowired
    public RoomController(ChatRoomService chatRoomService,ChatService chatService){
        this.chatRoomService = chatRoomService;
        this.chatService = chatService;
    }
    // 채팅방 목록
    @GetMapping(value="/rooms")
    @ResponseBody
    public HashMap<String,Object> rooms(){
        HashMap<String,Object> resultMap = new HashMap<>();
        try{
            ArrayList<ChatRoom> roomLists = chatRoomService.selectChatRoomLists();
            resultMap.put("result",roomLists);
            resultMap.put("status","success");

        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message",e.getStackTrace());
        }
        return resultMap;
    }

    // 채팅방 생성
    @PostMapping(value="/room")
    public String createRoom(@RequestParam String name){
        ChatRoom room = new ChatRoom();
        // room 생성
        room.setRoom_name(name);
        chatRoomService.createRoom(room);
        return  "redirect:/chat/room/"+room.getRoom_id();
    }

    // 채팅방 조회
    @GetMapping(value="/room")
    public void searchRoom(String roomName, Model model){
        model.addAttribute("room",chatRoomService.findByRoomName(roomName));
    }

    // 채팅방 입장
    @GetMapping(value="/room/{id}")
    public ModelAndView joinChatRoom(@PathVariable("id")int id){
        ModelAndView mav = new ModelAndView();
        ArrayList<ChatMessage> chatMessages = chatService.selectChatMessageByRoomId(id);
        mav.addObject("messages",chatMessages);
        mav.setViewName("/chat/room");
        return mav;
    }

}
