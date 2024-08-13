package com.PranitDethe.ChatApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.PranitDethe.ChatApplication.Model.Chatroom;
import com.PranitDethe.ChatApplication.Service.ChatroomService;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatroomController {

    @Autowired
    private ChatroomService chatroomService;

    @GetMapping
    public List<Chatroom> getAllChatrooms() {
        return chatroomService.getAllChatrooms();
    }

    @PostMapping
    public Chatroom createChatroom(@RequestBody Chatroom chatroom) {
        return chatroomService.createChatroom(chatroom);
    }
}
