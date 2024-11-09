package org.tennis_bird.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.tennis_bird.core.services.ChatService;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;


    @MessageMapping("/sendSupportMessage")
    public void sendSupportMessage(String message) {
        chatService.sendMessageToSupport(message);
    }
}