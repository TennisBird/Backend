package org.tennis_bird.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllersTestSupport {
    @Autowired
    protected MockMvc mockMvc;
    protected final ObjectMapper mapper = new ObjectMapper();
    private static final String PERSON_CREATE_REQUEST_FILE_NAME = "api/person/create_person_request.json";
    private static final String TASK_CREATE_REQUEST_FILE_NAME = "api/task/create_task_request.json";
    private static final String REGISTER_PERSON_REQUEST_FILE_NAME = "api/person/register_person_request.json";
    //Header obtained after registering user from create_person_request.json
    protected static final String TEAM_NAME = "tennis_app";
    protected static final String NULL_RESPONSE = "null";
    protected static final String PERSON_BASE_URL = "/person/";
    protected static final String TEAM_BASE_URL = "/team/";
    protected static final String TASK_BASE_URL = "/task/";
    protected static final String WORKER_BASE_URL = "/worker/";
    protected static final String REGISTRATION_URL = "/api/auth/register";

    private String authHeader = "";

    protected boolean testWithCorrectUuid() {
        return true;
    }

    protected void registerPerson() throws Exception {
        String tokenResponse;
        tokenResponse = getResponseFromCreateRequest(
                REGISTRATION_URL,
                parseJSONIntoString(REGISTER_PERSON_REQUEST_FILE_NAME)
        );
        authHeader = "Bearer " + mapper.readTree(tokenResponse).get("token").asText();
    }

    protected String getPerson(String uuid) throws Exception {
        return getResponse(get(PERSON_BASE_URL.concat(uuid)));
    }

    protected String getTeam(String id) throws Exception {
        return getResponse(get(TEAM_BASE_URL.concat(id)));
    }

    protected String getWorker(String id) throws Exception {
        return getResponse(get(WORKER_BASE_URL.concat(id)));
    }

    protected String getTask(String code) throws Exception {
        return getResponse(get(TASK_BASE_URL.concat(code)));
    }

    protected String createPerson() throws Exception {
        return getResponseFromCreateRequest(
                PERSON_BASE_URL, parseJSONIntoString(PERSON_CREATE_REQUEST_FILE_NAME));
    }

    protected String createTask() throws Exception {
        return getResponseFromCreateRequest(
                TASK_BASE_URL, parseJSONIntoString(TASK_CREATE_REQUEST_FILE_NAME));
    }

    protected String createTeam(String name) throws Exception {
        return mockMvc.perform(post(TEAM_BASE_URL
                        .concat("?name=%s".formatted(name)))
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    protected String createWorker(String personUuid, String teamId) throws Exception {
        return mockMvc.perform(post(WORKER_BASE_URL
                        .concat("?person_id=%s&team_id=%s&person_role=developer".formatted(personUuid, teamId)))
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    protected String getResponseFromCreateRequest(String urlTemplate, String content) throws Exception {
        return mockMvc.perform(post(urlTemplate).content(content)
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    protected String getResponse(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    protected void equalsJsonFiles(String responseFile, String response) throws Exception {
        if (testWithCorrectUuid()) {
            assertJson(mapper.readTree(response))
                    .where()
                    .path("uuid").matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")
                    .keysInAnyOrder()
                    .arrayInAnyOrder()
                    .isEqualTo(parseJSONIntoString(responseFile));
        } else {
            assertJson(mapper.readTree(response))
                    .where()
                    .path("id").isIgnored()
                    .keysInAnyOrder()
                    .arrayInAnyOrder()
                    .isEqualTo(parseJSONIntoString(responseFile));
        }
    }

    protected String parseJSONIntoString(String filePath) {
        try {
            File file = ResourceUtils.getFile("classpath:" + filePath);
            return new String(Files.readAllBytes(file.toPath()));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            throw new RuntimeException();
        }
    }
}
