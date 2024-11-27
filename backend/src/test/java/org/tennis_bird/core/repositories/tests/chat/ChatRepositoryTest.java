package org.tennis_bird.core.repositories.tests.chat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.chat.ChatEntity;
import org.tennis_bird.core.repositories.TestRepositorySupport;
import org.tennis_bird.core.repositories.chat.ChatRepository;

class ChatRepositoryTest extends TestRepositorySupport {
    @Autowired
    ChatRepository chatRepository;
    @Test
    void testCreateAndFindPerson() {
        ChatEntity chat = saveChatEntity();
        Assertions.assertTrue(chatRepository.findById(chat.getId()).isPresent());
    }

    @Test
    void testDeletePerson() {
        ChatEntity chat = saveChatEntity();
        Assertions.assertTrue(chatRepository.findById(chat.getId()).isPresent());

        chatRepository.delete(chat);
        Assertions.assertFalse(chatRepository.findById(chat.getId()).isPresent());
    }
}
