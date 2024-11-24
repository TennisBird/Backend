package org.tennis_bird.core.repositories.tests.chat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.core.entities.chat.ChatMemberEntity;
import org.tennis_bird.core.repositories.TestRepositorySupport;
import org.tennis_bird.core.repositories.chat.ChatMemberRepository;

class ChatMemberRepositoryTest extends TestRepositorySupport {
    @Autowired
    ChatMemberRepository chatMemberRepository;
    @Test
    void testCreateAndFindPerson() {
        ChatMemberEntity chatMember = saveChatMemberEntity();
        Assertions.assertTrue(chatMemberRepository.findById(chatMember.getId()).isPresent());
    }

    @Test
    void testDeletePerson() {
        ChatMemberEntity chatMember = saveChatMemberEntity();
        Assertions.assertTrue(chatMemberRepository.findById(chatMember.getId()).isPresent());

        chatMemberRepository.delete(chatMember);
        Assertions.assertFalse(chatMemberRepository.findById(chatMember.getId()).isPresent());
    }
}
