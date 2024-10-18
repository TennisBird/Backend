package org.tennis_bird.api.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.TeamRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

public class TeamControllerTests extends ControllersTestSupport {
    @Autowired
    private TeamRepository teamRepository;
    protected final String NEW_TEAM_NAME = "tennis_bird_app";

    @Override
    protected boolean testWithCorrectUuid() {
        return false;
    }

    @BeforeEach
    public void resetDb() {
        teamRepository.deleteAll();
    }

    @Test
    public void testCreateTeam() throws Exception {
        assertCorrectTeamBodyResponse(createTeam(TEAM_NAME));
    }
    @Test
    public void testGetTeam() throws Exception {
        String id = mapper.readTree(createTeam(TEAM_NAME)).get("id").asText();
        assertCorrectTeamBodyResponse(getTeam(id));
    }
    @Test
    public void testDeleteTeam() throws Exception {
        String id = mapper.readTree(createTeam(TEAM_NAME)).get("id").asText();
        assertCorrectTeamBodyResponse(getTeam(id));

        assertEquals(getResponse(delete(TEAM_BASE_URL.concat(id))), "true");
        assertEquals(NULL_RESPONSE, getTeam(id));
    }
    @Test
    public void testUpdateTeam() throws Exception {
        String id = mapper.readTree(createTeam(TEAM_NAME)).get("id").asText();
        assertEquals(mapper.readTree(getTeam(id)).get("name").asText(), TEAM_NAME);

        getResponse(put(TEAM_BASE_URL
                .concat(id)
                .concat("/name?name=%s".formatted(NEW_TEAM_NAME))));
        assertEquals(mapper.readTree(getTeam(id)).get("name").asText(), NEW_TEAM_NAME);
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
