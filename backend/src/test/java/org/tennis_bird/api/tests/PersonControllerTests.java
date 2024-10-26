package org.tennis_bird.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class PersonControllerTests extends ControllersTestSupport {
    @Autowired
    private PersonRepository personRepository;
    private final String PERSON_BODY_FILE_NAME = "api/person/person_test_body_response.json";
    @BeforeEach
    public void resetDb() {
        personRepository.deleteAll();
    }
    @Test
    public void testCreatePerson() throws Exception {
        equalsJsonFiles(PERSON_BODY_FILE_NAME, createPerson());
    }
    @Test
    public void testGetPerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        equalsJsonFiles(PERSON_BODY_FILE_NAME, getPerson(uuid));
    }
    @Test
    public void testDeletePerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        equalsJsonFiles(PERSON_BODY_FILE_NAME, getPerson(uuid));

        assertEquals(getResponse(delete(PERSON_BASE_URL.concat(uuid))), "true");
        assertEquals(NULL_RESPONSE, getPerson(uuid));
    }
    @Test
    public void testUpdatePerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        JsonNode beforeUpdateResponse = mapper.readTree(getPerson(uuid));
        assertEquals(beforeUpdateResponse.get("firstName").asText(), "Ivan");
        assertEquals(beforeUpdateResponse.get("lastName").asText(), "Ivanov");

        getResponse(put(PERSON_BASE_URL
                .concat(uuid)
                .concat("/?first_name=Vanya")));

        JsonNode afterUpdateResponse = mapper.readTree(getPerson(uuid));
        assertEquals(afterUpdateResponse.get("firstName").asText(), "Vanya");
        assertEquals(afterUpdateResponse.get("lastName").asText(), "Ivanov");
    }
}
