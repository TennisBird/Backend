package org.tennis_bird.core.repositories.tests.chat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.chat.ChatMessageEntity;
import org.tennis_bird.core.repositories.TestRepositorySupport;
import org.tennis_bird.core.repositories.chat.ChatMessageRepository;

class ChatMessageRepositoryTest extends TestRepositorySupport {
    @Autowired
    ChatMessageRepository chatMessageRepository;
    @Test
    void testCreateAndFindPerson() {
        ChatMessageEntity chatMessage = saveChatMessageEntity("something important");
        Assertions.assertTrue(chatMessageRepository.findById(chatMessage.getId()).isPresent());
    }

    @Test
    void testDeletePerson() {
        ChatMessageEntity chatMessage = saveChatMessageEntity("something important");
        Assertions.assertTrue(chatMessageRepository.findById(chatMessage.getId()).isPresent());

        chatMessageRepository.delete(chatMessage);
        Assertions.assertFalse(chatMessageRepository.findById(chatMessage.getId()).isPresent());
    }
}
