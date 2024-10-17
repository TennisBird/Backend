package org.tennis_bird.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonControllerTests extends ControllersTestSupport {
    @Autowired
    private PersonRepository personRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    public void resetDb() {
        personRepository.deleteAll();
    }
    @Test
    public void testCreatePerson() throws Exception {
        equalsJsonFiles("api/create_person_response.json", createPerson());
    }
    @Test
    public void testGetPerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        equalsJsonFiles("api/get_person_exist_response.json", getPerson(uuid));
    }
    @Test
    public void testDeletePerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        equalsJsonFiles("api/create_person_response.json", getPerson(uuid));
        assertEquals(deletePerson(uuid), "true");
        equalsJsonFiles("api/get_person_not_exist_response.json", getPerson(uuid));
    }
    @Test
    public void testUpdatePerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        JsonNode beforeUpdateResponse = mapper.readTree(getPerson(uuid));
        assertEquals(beforeUpdateResponse.get("firstName").asText(), "Ivan");
        assertEquals(beforeUpdateResponse.get("lastName").asText(), "Ivanov");
        assertEquals(beforeUpdateResponse.get("birthDate").asText(), "2005-10-09");
        updatePersonNameAndBirthDate(uuid);
        JsonNode afterUpdateResponse = mapper.readTree(getPerson(uuid));
        assertEquals(afterUpdateResponse.get("firstName").asText(), "Vanya");
        assertEquals(afterUpdateResponse.get("lastName").asText(), "Ivanov");
        assertEquals(afterUpdateResponse.get("birthDate").asText(), "2005-10-10");
    }

    private String getPerson(String uuid) throws Exception {
        return mockMvc.perform(get(String.format("/%s", uuid))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
    private String deletePerson(String uuid) throws Exception {
        return mockMvc.perform(delete(String.format("/%s", uuid))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
    private String updatePersonNameAndBirthDate(String uuid) throws Exception {
        return mockMvc.perform(put(String.format("/%s/?first_name=Vanya&birth_date=2005-10-10", uuid))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
