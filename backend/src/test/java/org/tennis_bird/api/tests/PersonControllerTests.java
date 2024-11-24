package org.tennis_bird.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class PersonControllerTests extends ControllersTestSupport {
    @Autowired
    private PersonRepository personRepository;
    private static final String PERSON_BODY_FILE_NAME = "api/person/person_test_body_response.json";
    @BeforeEach
    public void resetDb() throws Exception {
        personRepository.deleteAll();
        registerPerson();
    }
    @Test
    void testCreatePerson() throws Exception {
        equalsJsonFiles(PERSON_BODY_FILE_NAME, createPerson());
    }
    @Test
    void testGetPerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        equalsJsonFiles(PERSON_BODY_FILE_NAME, getPerson(uuid));
    }
    @Test
    void testDeletePerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        equalsJsonFiles(PERSON_BODY_FILE_NAME, getPerson(uuid));

        assertEquals("true", getResponse(delete(PERSON_BASE_URL.concat(uuid))));
        assertEquals(NULL_RESPONSE, getPerson(uuid));
    }
    @Test
    void testUpdatePerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        JsonNode beforeUpdateResponse = mapper.readTree(getPerson(uuid));
        assertEquals("Ivan", beforeUpdateResponse.get("firstName").asText());
        assertEquals("Ivanov", beforeUpdateResponse.get("lastName").asText());

        getResponse(put(PERSON_BASE_URL
                .concat(uuid)
                .concat("/?first_name=Vanya")));

        JsonNode afterUpdateResponse = mapper.readTree(getPerson(uuid));
        assertEquals("Vanya", afterUpdateResponse.get("firstName").asText());
        assertEquals("Ivanov", afterUpdateResponse.get("lastName").asText());
    }
}
