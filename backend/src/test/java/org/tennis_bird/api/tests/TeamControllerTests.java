package org.tennis_bird.api.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.PersonRepository;
import org.tennis_bird.core.repositories.TeamRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

class TeamControllerTests extends ControllersTestSupport {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TeamRepository teamRepository;
    protected static final String NEW_TEAM_NAME = "tennis_bird_app";

    @Override
    protected boolean testWithCorrectUuid() {
        return false;
    }

    @BeforeEach
    public void resetDb() throws Exception {
        teamRepository.deleteAll();
        personRepository.deleteAll();
        registerPerson();
    }

    @Test
    void testCreateTeam() throws Exception {
        assertCorrectTeamBodyResponse(createTeam(TEAM_NAME));
    }
    @Test
    void testGetTeam() throws Exception {
        String id = mapper.readTree(createTeam(TEAM_NAME)).get("id").asText();
        assertCorrectTeamBodyResponse(getTeam(id));
    }
    @Test
    void testDeleteTeam() throws Exception {
        String id = mapper.readTree(createTeam(TEAM_NAME)).get("id").asText();
        assertCorrectTeamBodyResponse(getTeam(id));

        assertEquals("true", getResponse(delete(TEAM_BASE_URL.concat(id))));
        assertEquals(NULL_RESPONSE, getTeam(id));
    }
    @Test
    void testUpdateTeam() throws Exception {
        String id = mapper.readTree(createTeam(TEAM_NAME)).get("id").asText();
        assertEquals(TEAM_NAME, mapper.readTree(getTeam(id)).get("name").asText());

        getResponse(put(TEAM_BASE_URL
                .concat(id)
                .concat("/name?name=%s".formatted(NEW_TEAM_NAME))));
        assertEquals(NEW_TEAM_NAME, mapper.readTree(getTeam(id)).get("name").asText());
    }

    private void assertCorrectTeamBodyResponse(String content) throws Exception {
        assertJson(mapper.readTree(content))
                .where()
                .path("id").isIgnored()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo("{\"id\": 2, \"name\" : \"%s\"}".formatted(TEAM_NAME));
    }
}
