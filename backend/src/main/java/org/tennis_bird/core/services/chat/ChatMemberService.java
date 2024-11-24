package org.tennis_bird.core.services.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.chat.ChatMemberEntity;
import org.tennis_bird.core.repositories.chat.ChatMemberRepository;

import java.util.List;
import java.util.Optional;

@Component
public class ChatMemberService {
    @Autowired
    private ChatMemberRepository repository;

    private static final Logger logger = LogManager.getLogger(ChatMemberService.class.getName());

    public ChatMemberEntity create(ChatMemberEntity chatMember) {
        logger.info("Creating chat member with id {}", chatMember.getId());
        return repository.save(chatMember);
    }

    public Optional<ChatMemberEntity> find(Long chatMemberId) {
        logger.info("Finding chat member with id {}", chatMemberId);
        return repository.findById(chatMemberId);
    }

    public List<ChatMemberEntity> findAll() {
        logger.info("Finding all chat members");
        return repository.findAll();
    }

    public boolean delete(Long id) {
        logger.info("Deleting chat member with id {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
