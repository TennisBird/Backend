package org.tennis_bird.core.repositories.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tennis_bird.core.entities.chat.ChatMessageEntity;

import java.util.List;

public interface ChatMessageRepository  extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findByChatId(Long chatId);
}
