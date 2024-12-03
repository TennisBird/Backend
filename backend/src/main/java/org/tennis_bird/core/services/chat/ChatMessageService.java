package org.tennis_bird.core.services.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tennis_bird.core.entities.chat.ChatMessageEntity;
import org.tennis_bird.core.repositories.chat.ChatMessageRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository repository;

    private static final Logger logger = LogManager.getLogger(ChatMessageService.class.getName());

    public ChatMessageEntity create(ChatMessageEntity chatMessage) {
        logger.info("Creating chat message with id {}", chatMessage.getId());
        chatMessage.setTimestamp(new Date());
        return repository.save(chatMessage);
    }

    public Optional<ChatMessageEntity> find(Long chatMessageId) {
        logger.info("Finding chat message with id {}", chatMessageId);
        return repository.findById(chatMessageId);
    }

    public List<ChatMessageEntity> findAll() {
        logger.info("Finding all chat messages");
        return repository.findAll();
    }

    public boolean delete(Long id) {
        logger.info("Deleting chat message with id {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ChatMessageEntity> findByChatId(Long chatId) {
        logger.info("Finding messages for chat id {}", chatId);
        return repository.findByChatId(chatId);
    }
}
