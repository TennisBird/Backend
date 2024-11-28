package org.tennis_bird.api.tests.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.PersonRepository;
import org.tennis_bird.core.repositories.chat.ChatMemberRepository;
import org.tennis_bird.core.repositories.chat.ChatMessageRepository;
import org.tennis_bird.core.repositories.chat.ChatRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

class ChatMassageControllerTest extends ControllersTestSupport {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatMemberRepository chatMemberRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @BeforeEach
    public void resetDb() throws Exception {
        personRepository.deleteAll();
        chatRepository.deleteAll();
        chatMemberRepository.deleteAll();
        registerPerson();
    }

    @Test
    void testCreateChatMessage() throws Exception {
        JsonNode person = mapper.readTree(createPerson());
        JsonNode chat = mapper.readTree(createChatByMembers(CHAT_NAME, List.of(person.get("uuid").asText())));
        //TODO but i want array here
        JsonNode member = mapper.readTree(getChatMembers(chat.get("id").asText()));
        JsonNode actualMessage = mapper.readTree(createChatMessage(
                chat.get("id").asText(), member.get("id").asText(), "\"very not important text\""));

        ObjectNode expectedMessage = mapper.createObjectNode();
        expectedMessage.set("sender", member);
        expectedMessage.set("chat", chat);
        expectedMessage.set("timestamp", null);
        expectedMessage.set("content", mapper.readTree("\"very not important text\""));
        expectedMessage.put("id", 2);

//        assertJson(actualMessage)
//                .where()
//                .path("id").isIgnored()
//                .path("timestamp").isIgnored()
//                .arrayInAnyOrder()
//                .keysInAnyOrder()
//                .isEqualTo(expectedMessage);
    }

    @Test
    void testMessagesByChatId() throws Exception {
        JsonNode person = mapper.readTree(createPerson());
        JsonNode chat = mapper.readTree(createChatByMembers(CHAT_NAME, List.of(person.get("uuid").asText())));
        JsonNode member = mapper.readTree(getChatMembers(chat.get("id").asText()));
        JsonNode message1 = mapper.readTree(createChatMessage(chat.get("id").asText(), member.get("id").asText(), MESS_CONTEXT));
        JsonNode message2 = mapper.readTree(createChatMessage(chat.get("id").asText(), member.get("id").asText(), "OK?"));

        JsonNode actualMessage = mapper.readTree(getResponse(get(CHAT_MESSAGE_BASE_URL
                .concat("?chat_id=%s".formatted(chat.get("id").asText())))));

        ObjectNode expectedMessage1 = mapper.createObjectNode();
        expectedMessage1.set("member", member);
        expectedMessage1.set("chat", chat);
//        expectedMessage1.set("context", mapper.readTree(MESS_CONTEXT));
//        expectedMessage1.put("id", 2);
//        ObjectNode expectedMessage2 = mapper.createObjectNode();
//        expectedMessage2.set("member", member);
//        expectedMessage2.set("chat", chat);
//        expectedMessage2.set("context", mapper.readTree("OK?"));
//        expectedMessage2.put("id", 3);
//        List<ObjectNode> nodes = new ArrayList<>();
//        nodes.add(expectedMessage1);
//        nodes.add(expectedMessage2);

//        assertJson(actualMessage)
//                .where()
//                .path("id").isIgnored()
//                .arrayInAnyOrder()
//                .keysInAnyOrder()
//                .isEqualTo(nodes);
    }
    @Test
    void testGetChatMessage() throws Exception {
        JsonNode person = mapper.readTree(createPerson());
        JsonNode chat = mapper.readTree(createChatByMembers(CHAT_NAME, List.of(person.get("uuid").asText())));
        JsonNode member = mapper.readTree(getChatMembers(chat.get("id").asText()));
        JsonNode message = mapper.readTree(createChatMessage(chat.get("id").asText(), member.get("id").asText(), MESS_CONTEXT));

        JsonNode actualMessage = mapper.readTree(getResponse(get(CHAT_MESSAGE_BASE_URL
                .concat(message.get("id").asText()))));

        ObjectNode expectedMessage = mapper.createObjectNode();
        expectedMessage.set("member", member);
        expectedMessage.set("chat", chat);
       // expectedMessage.set("context", mapper.readTree(MESS_CONTEXT));
       // expectedMessage.put("id", 2);

//        assertJson(actualMessage)
//                .where()
//                .path("id").isIgnored()
//                .arrayInAnyOrder()
//                .keysInAnyOrder()
//                .isEqualTo(expectedMessage);
    }
    @Test
    void testDeleteChatMessage() throws Exception {
        JsonNode person = mapper.readTree(createPerson());
        JsonNode chat = mapper.readTree(createChatByMembers(CHAT_NAME, List.of(person.get("uuid").asText())));
        JsonNode member = mapper.readTree(getChatMembers(chat.get("id").asText()));
        JsonNode message = mapper.readTree(createChatMessage(chat.get("id").asText(), member.get("id").asText(), MESS_CONTEXT));

        assertEquals("true", getResponse(delete(CHAT_MESSAGE_BASE_URL
                .concat(message.get("id").asText()))));
        assertEquals(NULL_RESPONSE, getChatMembers(message.get("id").asText()));
    }
}
