package org.tennis_bird.api.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.tennis_bird.core.services.chat.ChatService;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    private static final Logger logger = LogManager.getLogger(ChatController.class.getName());

    @DeleteMapping(path = "/chat/{id}",
            produces = "application/json")
    public boolean deleteChat(@PathVariable(value = "id") Long id) {
        logger.info("Attempting to delete chat with ID: {}", id);
        return chatService.delete(id);
    }

//    @MessageMapping("/sendSupportMessage")
//    public void sendSupportMessage(String message) {
//        chatService.sendMessageToSupport(message);
//    }
}
