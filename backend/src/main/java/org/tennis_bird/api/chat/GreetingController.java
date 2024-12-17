package org.tennis_bird.api.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.chat.ChatEntity;
import org.tennis_bird.core.entities.chat.ChatMemberEntity;
import org.tennis_bird.core.entities.chat.ChatMessageEntity;
import org.tennis_bird.core.repositories.chat.ChatMessageRepository;
import org.tennis_bird.core.services.PersonService;
import org.tennis_bird.core.services.chat.ChatMemberService;
import org.tennis_bird.core.services.chat.ChatMessageService;
import org.tennis_bird.core.services.chat.ChatService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatMemberService chatMemberService;
    @Autowired
    private PersonService personService;
    @Autowired
    private ChatService chatService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.name()) + "!");
    }

    @MessageMapping("chat-messages")
    @SendTo("/chat/messages")
    public void getMessagesCurrentChat(Long chatId, Principal principal) {
        List<ChatMessageEntity> messagesChat = chatMessageService.findByChatId(chatId);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/messages", messagesChat);
    }

    @MessageMapping("/create-chat")
    public void createChatWithUsers(@Payload CreateChatRequest request) {

        ChatEntity chat = chatService.create(new ChatEntity(null, request.name()));
        Optional<ChatEntity> chatO = chatService.find(chat.getId());
        for(var personId : request.personIds()) {
            Optional<PersonEntity> personO = personService.find(personId);
            if (chatO.isPresent() && personO.isPresent()) {
                ChatMemberEntity chatMember = new ChatMemberEntity(
                        null, chatO.get(), personO.get());
                chatMemberService.create(chatMember);
            }
        }
    }
    @MessageMapping("/chat/{chatId}")
    public void sendMessageToChat(@DestinationVariable Long chatId, HelloMessage message) {
        List<String> usernames = chatMemberService.findByChatId(chatId).stream().map(m->m.getMember()
                .getUsername()).toList();

        for (String username : usernames) {
            //simpMessagingTemplate.convertAndSendToUser(userId, "/queue/messages/" + chatId, message);
            simpMessagingTemplate.convertAndSend("/topic/chats/"+username+"/"+chatId, new Greeting("Hello, " + HtmlUtils.htmlEscape(message.name()) + "?"));
        }
    }
}