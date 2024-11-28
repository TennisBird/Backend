package org.tennis_bird.api.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tennis_bird.core.entities.chat.ChatEntity;
import org.tennis_bird.core.entities.chat.ChatMemberEntity;
import org.tennis_bird.core.entities.chat.ChatMessageEntity;
import org.tennis_bird.core.services.chat.ChatMemberService;
import org.tennis_bird.core.services.chat.ChatMessageService;
import org.tennis_bird.core.services.chat.ChatService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMemberService chatMemberService;

    private static final Logger logger = LogManager.getLogger(ChatMessageController.class.getName());

//    @MessageMapping("/chat/{chatId}/send") // Эндпоинт для отправки сообщения в конкретный чат
//    @SendTo("/topic/chat/{chatId}/messages") // Отправка сообщения подписчикам конкретного чата
//    public ChatMessageEntity sendMessage(@DestinationVariable Long chatId, ChatMessageEntity message) {
//        logger.info("Sending message in chat {} from sender {}", chatId, message.getSender().getId());
//
//        Optional<ChatEntity> chat = chatService.find(chatId);
//        Optional<ChatMemberEntity> sender = chatMemberService.find(message.getSender().getId());
//
//        if (chat.isPresent() && sender.isPresent()) {
//            ChatMessageEntity chatMessage = new ChatMessageEntity(null, chat.get(), sender.get(), message.getContent(), new Date());
//            ChatMessageEntity createdMessage = chatMessageService.create(chatMessage);
//            return createdMessage; // Возвращаем созданное сообщение для отправки всем подписчикам
//        }
//        return null; // Если чат или отправитель не найдены
//    }
//
//    @MessageMapping("/chat/{chatId}/delete/{messageId}") // Эндпоинт для удаления сообщения через WebSocket
//    @SendTo("/topic/chat/{chatId}/messages") // Уведомляем подписчиков о том, что сообщение удалено
//    public Long deleteMessage(@DestinationVariable Long chatId, @DestinationVariable Long messageId) {
//        logger.info("Deleting message with id {} from chat {}", messageId, chatId);
//
//        // Удаляем сообщение по его идентификатору
//        boolean result = chatMessageService.delete(messageId);
//        if (result) {
//            return messageId; // Возвращаем идентификатор удаленного сообщения для уведомления подписчиков
//        }
//
//        return null; // Если удаление не удалось
//    }




    @MessageMapping("/message")
    @SendTo("/chat/public")
    public ChatMessage receivePublicMessage(@Payload ChatMessage message) {
        return message;
    }






    @PostMapping(path = "/chat/message/",
            produces = "application/json")
    public Optional<ChatMessageEntity> createChatMessage(
            @RequestParam(value = "chat_id") Long chatId,
            @RequestParam(value = "sender_id") Long senderId,
            @RequestParam(value = "content") String content) {

        logger.info("Creating chat message in chat {} from sender {}", chatId, senderId);

        Optional<ChatEntity> chat = chatService.find(chatId);
        Optional<ChatMemberEntity> sender = chatMemberService.find(senderId);

        if (chat.isPresent() && sender.isPresent()) {
            ChatMessageEntity chatMessage = new ChatMessageEntity(null, chat.get(), sender.get(), content, new Date());
            ChatMessageEntity createdMessage = chatMessageService.create(chatMessage);
            return Optional.of(createdMessage);
        }
        return Optional.empty();
    }

    @GetMapping("/chat/message/{message_id}")
    public Optional<ChatMessageEntity> getChatMessage(@PathVariable(value = "message_id") Long messageId) {
        logger.info("Getting chat message by id {}", messageId);
        return chatMessageService.find(messageId);
    }

    @DeleteMapping("/chat/message/{message_id}")
    public boolean deleteChatMessage(@PathVariable(value = "message_id") Long messageId) {
        logger.info("Attempting to delete chat message with id: {}", messageId);
        return chatMessageService.delete(messageId);
    }

    @GetMapping("/chat/message/")
    public List<ChatMessageEntity> getMessagesByChatId(@RequestParam(value = "chat_id") Long chatId) {
        logger.info("Getting messages for chat id {}", chatId);
        return chatMessageService.findByChatId(chatId);
    }
    //TODO change context
}