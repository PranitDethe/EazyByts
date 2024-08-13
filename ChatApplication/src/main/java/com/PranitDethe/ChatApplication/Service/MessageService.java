package com.PranitDethe.ChatApplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PranitDethe.ChatApplication.Model.Message;
import com.PranitDethe.ChatApplication.Repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChatroom(Long chatroomId) {
        return messageRepository.findByChatroom_IdOrderByTimestampAsc(chatroomId);
    }
}
