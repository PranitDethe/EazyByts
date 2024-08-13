package com.PranitDethe.ChatApplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PranitDethe.ChatApplication.Model.Chatroom;
import com.PranitDethe.ChatApplication.Repository.ChatroomRepository;

import java.util.List;

@Service
public class ChatroomService {
    @Autowired
    private ChatroomRepository chatroomRepository;

    public List<Chatroom> getAllChatrooms() {
        return chatroomRepository.findAll();
    }

    public Chatroom createChatroom(Chatroom chatroom) {
        return chatroomRepository.save(chatroom);
    }
}
