package org.tennis_bird.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

public class TaskControllerTests extends ControllersTestSupport {
    @Autowired
    private TaskRepository taskRepository;
    private final String TASK_BODY_FILE_NAME = "api/task/task_test_body_response.json";

    @Override
    protected boolean testWithCorrectUuid() {
        return false;
    }
    @BeforeEach
    public void resetDb() {
        taskRepository.deleteAll();
    }

    @Test
    public void testCreateTask() throws Exception {
        equalsJsonFiles(TASK_BODY_FILE_NAME, createTask());
    }
    @Test
    public void testGetTask() throws Exception {
        String code = mapper.readTree(createTask()).get("code").asText();
        equalsJsonFiles(TASK_BODY_FILE_NAME, getTask(code));
    }
    @Test
    public void testDeleteTask() throws Exception {
        String code = mapper.readTree(createTask()).get("code").asText();
        equalsJsonFiles(TASK_BODY_FILE_NAME, getTask(code));

        assertEquals(getResponse(delete(TASK_BASE_URL.concat(code))), "true");
        assertEquals(NULL_RESPONSE, getTask(code));
    }
    @Test
    public void testUpdateTask() throws Exception {
        String code = mapper.readTree(createTask()).get("code").asText();
        JsonNode beforeUpdateResponse = mapper.readTree(getTask(code));
        assertEquals(beforeUpdateResponse.get("title").asText(), "create schema");
        assertEquals(beforeUpdateResponse.get("code").asText(), "DB-001");
        assertEquals(beforeUpdateResponse.get("estimate").asText(), "null");

        getResponse(put(TASK_BASE_URL
                .concat(code)
                .concat("/?title=create schema and data&estimate=3")));

        JsonNode afterUpdateResponse = mapper.readTree(getTask(code));
        assertEquals(afterUpdateResponse.get("title").asText(), "create schema and data");
        assertEquals(afterUpdateResponse.get("code").asText(), "DB-001");
        assertEquals(afterUpdateResponse.get("estimate").asText(), "3");
    }
    @Test
    public void testAuthorOnTask() throws Exception {
        String code = mapper.readTree(createTask()).get("code").asText();
        String personUuid = mapper.readTree(createPerson()).get("uuid").asText();
        String teamId1 = mapper.readTree(createTeam("tennis_app")).get("id").asText();
        String teamId2 = mapper.readTree(createTeam("bird_app")).get("id").asText();
        String worker1 = createWorker(personUuid, teamId1);
        String worker2 = createWorker(personUuid, teamId2);

        assertEquals("true", setAuthorOnTask(code, mapper.readTree(worker1).get("id").asText()));
        assertEquals("true", setAuthorOnTask(code, mapper.readTree(worker2).get("id").asText()));

        ArrayNode expectedAuthors = mapper.createArrayNode();
        expectedAuthors.add(mapper.readTree(worker1));
        expectedAuthors.add(mapper.readTree(worker2));

        JsonNode actualAuthors = mapper.readTree(getResponse(get(TASK_BASE_URL
                .concat(code)
                .concat("/role/author"))));

        assertJson(actualAuthors)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(expectedAuthors);
    }

    private String setAuthorOnTask(String code, String workerId) throws Exception {
        return getResponse(post(TASK_BASE_URL
                .concat(code)
                .concat("/role/author?worker_id=%s".formatted(workerId))));
    }
}
