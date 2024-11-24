package org.tennis_bird.core.services.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tennis_bird.core.entities.chat.ChatEntity;
import org.tennis_bird.core.repositories.chat.ChatRepository;

import java.util.Optional;

@Service
public class ChatService {

    //private final SimpMessagingTemplate messagingTemplate;
    //private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    @Autowired
    private ChatRepository repository;
    private static final Logger logger = LogManager.getLogger(ChatService.class.getName());

    public ChatEntity create(ChatEntity chat) {
        logger.info("create team with id {}", chat.getId());
        return repository.save(chat);
    }
    public Optional<ChatEntity> find(Long chatId) {
        logger.info("Finding chat member with id {}", chatId);
        return repository.findById(chatId);
    }
    public boolean delete(Long id) {
        logger.info("delete task with id {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

//    public ChatService(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    public void addUser(String username) {
//        onlineUsers.add(username);
//    }
//
//    public void removeUser(String username) {
//        onlineUsers.remove(username);
//    }
//
//    public void sendMessageToSupport(String message) {
//        for (String user : onlineUsers) {
//            messagingTemplate.convertAndSend("/topic/support/" + user, message);
//        }
//    }
}