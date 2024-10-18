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
    private final String PERSON_CREATE_REQUEST_FILE_NAME = "api/person/create_person_request.json";
    private final String TASK_CREATE_REQUEST_FILE_NAME = "api/task/create_task_request.json";
    protected final String TEAM_NAME = "tennis_app";
    protected final String NULL_RESPONSE = "null";
    protected final String PERSON_BASE_URL = "/";
    protected final String TEAM_BASE_URL = "/team/";
    protected final String TASK_BASE_URL = "/task/";
    protected final String WORKER_BASE_URL = "/worker/";

    protected boolean testWithCorrectUuid() {
        return true;
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
        return getResponseFromCreateRequest(
                TEAM_BASE_URL, "{ \"name\" : \"%s\"}".formatted(name));
    }
    protected String createWorker(String personUuid, String teamId) throws Exception {
        return mockMvc.perform(post(WORKER_BASE_URL
                        .concat("?person_id=%s&team_id=%s&person_role=developer".formatted(personUuid, teamId)))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
    protected String getResponseFromCreateRequest(String urlTemplate, String content) throws Exception {
        return mockMvc.perform(post(urlTemplate).content(content)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
    protected String getResponse(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
    protected void equalsJsonFiles(String responseFile, String response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
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
