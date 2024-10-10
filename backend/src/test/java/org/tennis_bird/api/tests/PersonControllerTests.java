package org.tennis_bird.api.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.tennis_bird.api.ControllersTestSupport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonControllerTests extends ControllersTestSupport {
    @Test
    public void testCreatePersonRequest() throws Exception {
        equalsJsonFiles("api/create_person_response.json", createPerson());
    }
    @Test
    public void testRequest() throws Exception {
        equalsJsonFiles("api/create_person_response.json", getResponse());
    }
    private String getResponse() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String createResponse = createPerson();
        String uuid = mapper.readTree(createResponse).get("uuid").asText();
        return mockMvc.perform(get(String.format("/%s", uuid))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
