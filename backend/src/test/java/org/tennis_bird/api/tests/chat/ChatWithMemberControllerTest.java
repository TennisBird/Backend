package org.tennis_bird.api.tests.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.PersonRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class ChatWithMemberControllerTest extends ControllersTestSupport {
    @Autowired
    private PersonRepository personRepository;
    private static final String PERSON_BODY_FILE_NAME = "api/person/person_test_body_response.json";
    @BeforeEach
    public void resetDb() throws Exception {
        personRepository.deleteAll();
        registerPerson();
    }

    @Test
    void testCreateChatByMembers() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        equalsJsonFiles(PERSON_BODY_FILE_NAME, createChatByMembers(List.of(uuid)));
    }
    @Test
    void testGetChatMember() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        String id = mapper.readTree(createChatByMembers(List.of(uuid))).get("id").asText();
        equalsJsonFiles(PERSON_BODY_FILE_NAME, getChatMembers(id));
    }
    @Test
    void testDeleteChatMember() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        String id = mapper.readTree(createChatByMembers(List.of(uuid))).get("id").asText();
        equalsJsonFiles(PERSON_BODY_FILE_NAME, getChatMembers(id));

        assertEquals("true", getResponse(delete(CHAT_MEMBER_BASE_URL.concat(id))));
        assertEquals(NULL_RESPONSE, getChatMembers(id));
    }
    @Test
    void testDeleteChat() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        String id = mapper.readTree(createChatByMembers(List.of(uuid))).get("id").asText();
        equalsJsonFiles(PERSON_BODY_FILE_NAME, getChatMembers(id));

        assertEquals("true", getResponse(delete(CHAT_BASE_URL.concat(id))));
        assertEquals(NULL_RESPONSE, getPerson(id));
    }
}
