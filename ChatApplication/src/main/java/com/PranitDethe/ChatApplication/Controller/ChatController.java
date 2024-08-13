package com.PranitDethe.ChatApplication.Controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.PranitDethe.ChatApplication.Model.Message;
import com.PranitDethe.ChatApplication.Service.MessageService;
import com.PranitDethe.ChatApplication.Service.UserService;

@Controller
public class ChatController {

    private final MessageService messageService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(MessageService messageService, UserService userService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send/{room}")
    public void sendMessage(@DestinationVariable String room, Message message, SimpMessageHeaderAccessor headerAccessor) {
        messageService.saveMessage(message);
        messagingTemplate.convertAndSend("/topic/" + room, message);
    }
}