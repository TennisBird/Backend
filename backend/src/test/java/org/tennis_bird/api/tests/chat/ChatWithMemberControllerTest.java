package org.tennis_bird.api.tests.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.PersonRepository;
import org.tennis_bird.core.repositories.chat.ChatMemberRepository;
import org.tennis_bird.core.repositories.chat.ChatRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

public class ChatWithMemberControllerTest extends ControllersTestSupport {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatMemberRepository chatMemberRepository;
    private static final String CHAT_NAME = "backenders";
    @BeforeEach
    public void resetDb() throws Exception {
        personRepository.deleteAll();
        chatRepository.deleteAll();
        chatMemberRepository.deleteAll();
        registerPerson();
    }

    @Test
    void testCreateChatByMembers() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        assertCorrectChatBodyResponse(createChatByMembers(CHAT_NAME, List.of(uuid)));
    }
    private void assertCorrectChatBodyResponse(String content) throws Exception {
        assertJson(mapper.readTree(content))
                .where()
                .path("id").isIgnored()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo("{\"id\": 2, \"name\": \"%s\"}".formatted(CHAT_NAME));
    }
    @Test
    void testGetChatMembers() throws Exception {
        JsonNode person = mapper.readTree(createPerson());
        JsonNode chat = mapper.readTree(createChatByMembers(CHAT_NAME, List.of(person.get("uuid").asText())));

        JsonNode actualMembers = mapper.readTree(getResponse(get(CHAT_MEMBER_BASE_URL.concat(chat.get("id").asText()))));

        ObjectNode expectedMembers = mapper.createObjectNode();
        expectedMembers.set("person", person);
        expectedMembers.set("chat", chat);
        expectedMembers.put("id", 2);

        assertJson(actualMembers)
                .where()
                .path("id").isIgnored()
                .arrayInAnyOrder()
                .keysInAnyOrder()
                .isEqualTo(expectedMembers);
    }
    @Test
    void testDeleteChatMember() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        String id = mapper.readTree(createChatByMembers(CHAT_NAME, List.of(uuid))).get("id").asText();

        assertEquals("true", getResponse(delete(CHAT_MEMBER_BASE_URL
                .concat("?chat_id=%s&person_id=%s".formatted(id, uuid)))));
        assertEquals(NULL_RESPONSE, getChatMembers(id));
    }
    @Test
    void testDeleteChat() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        String id = mapper.readTree(createChatByMembers(CHAT_NAME, List.of(uuid))).get("id").asText();

        assertEquals("true", getResponse(delete(CHAT_BASE_URL.concat(id))));
        assertEquals(NULL_RESPONSE, getChatMembers(id));
    }
    //TODO update chat
}
