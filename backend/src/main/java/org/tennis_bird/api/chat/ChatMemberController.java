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
import java.util.Optional;
import java.util.UUID;

@RestController
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
    public List<ChatMemberInfoResponse> getChatMembers(@PathVariable(value = "id") Long id) {
        logger.info("Getting chat members by chat id {}", id);
        return chatMemberService.findByChatId(id).stream().map(converter::entityToResponse).toList();
    }

    @DeleteMapping("/chat/member/")
    public boolean deletePersonFromChat(
            @RequestParam(value = "chat_id") Long chatId,
            @RequestParam(value = "person_id") UUID personId
    ) {
        logger.info("Attempting to delete person with id: {} from chat with id: {}", personId, chatId);
        return chatMemberService.deleteByChatAndPersonId(chatId, personId);
    }

    @PostMapping(path = "/chat/",
            produces = "application/json")
    public Optional<ChatEntity> createChatByMembers(
            @RequestParam(value = "chat_name") String name,
            @RequestParam(value = "person_ids") List<UUID> personIds
    ) {

        logger.info("Creating chat {} with members personIds {}", personIds);

        ChatEntity chat = chatService.create(new ChatEntity(null, name));
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

    @GetMapping("/chat/member/")
    public List<Long> getChatIdsByUserName(@RequestParam(value = "username") String username) {
        return chatMemberService.findChatsByUsername(username)
                .stream().map(ChatEntity::getId).toList();
    }
}