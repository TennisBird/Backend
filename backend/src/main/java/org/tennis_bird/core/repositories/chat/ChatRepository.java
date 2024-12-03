package org.tennis_bird.core.repositories.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tennis_bird.core.entities.chat.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> { }
