package org.tennis_bird.api.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tennis_bird.api.data.ChatMemberInfoResponse;
import org.tennis_bird.api.data.InfoConverter;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.chat.ChatEntity;
import org.tennis_bird.core.entities.chat.ChatMemberEntity;
import org.tennis_bird.core.services.PersonService;
import org.tennis_bird.core.services.chat.ChatMemberService;
import org.tennis_bird.core.services.chat.ChatService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/chat/member")
public class ChatMemberController {
    @Autowired
    private ChatMemberService chatMemberService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private PersonService personService;
    @Autowired
    InfoConverter converter;

    private static final Logger logger = LogManager.getLogger(ChatMemberController.class.getName());

    @PostMapping(path = "/chat/member/",
            produces = "application/json")
    public Optional<ChatMemberInfoResponse> createChatMember(
            @RequestParam(value = "chat_id") Long chatId,
            @RequestParam(value = "person_id") UUID personId) {

        logger.info("Creating chat member with chatId {} and personId {}", chatId, personId);

        Optional<ChatEntity> chat = chatService.find(chatId);
        Optional<PersonEntity> person = personService.find(personId);

        if (chat.isPresent() && person.isPresent()) {
            ChatMemberEntity chatMember = new ChatMemberEntity(null, chat.get(), person.get());
            return Optional.of(converter.entityToResponse(chatMemberService.create(chatMember)));
        }
        return Optional.empty();
    }

    @GetMapping("/chat/member/{id}")
    public Optional<ChatMemberInfoResponse> getChatMember(@PathVariable(value = "id") Long id) {
        logger.info("Getting chat member by id {}", id);
        return chatMemberService.find(id).map(converter::entityToResponse);
    }

    @DeleteMapping("/chat/member/{id}")
    public boolean deleteChatMember(@PathVariable(value = "id") Long id) {
        logger.info("Attempting to delete chat member with id: {}", id);
        return chatMemberService.delete(id);
    }

    @PostMapping(path = "/chat/",
            produces = "application/json")
    public Optional<ChatEntity> createChatByMembers(
            @RequestParam(value = "person_id") List<UUID> personIds
    ) {

        logger.info("Creating chat member with personIds {}", personIds);

        ChatEntity chat = chatService.create(new ChatEntity(null));
        Optional<ChatEntity> chatO = chatService.find(chat.getId());
        for(var personId : personIds) {
            Optional<PersonEntity> personO = personService.find(personId);
            if (chatO.isPresent() && personO.isPresent()) {
                ChatMemberEntity chatMember = new ChatMemberEntity(null, chatO.get(), personO.get());
                chatMemberService.create(chatMember);
            }
        }

        return chatO;
    }

}