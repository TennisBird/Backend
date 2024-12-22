package org.tennis_bird.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.tennis_bird.api.ControllersTestSupport;
import org.tennis_bird.core.repositories.PersonRepository;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class PersonControllerTests extends ControllersTestSupport {
    @Autowired
    private PersonRepository personRepository;
    private static final String PERSON_BODY_FILE_NAME = "api/person/person_test_body_response.json";
    private static final String BASE_AVATAR_PATH = "avatars/base.png";

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
    void testAvatarUpdatePerson() throws Exception {
        String uuid = mapper.readTree(createPerson()).get("uuid").asText();
        JsonNode beforeUpdateResponse = mapper.readTree(getPerson(uuid));
        assertEquals(beforeUpdateResponse.get("avatarPath").asText(), "avatars/base.png");

        InputStream input = new FileInputStream(BASE_AVATAR_PATH);
        MockMultipartFile file = new MockMultipartFile("avatar","file21312", "image/png", input);

        getResponse(multipart(PERSON_BASE_URL + uuid + "/avatar/update/")
                .file(file));

        JsonNode afterUpdateResponse = mapper.readTree(getPerson(uuid));

        //test changing in database
        assertEquals(afterUpdateResponse.get("avatarPath").asText(), "avatars/" + uuid + ".png");

        //test that file with avatar is actually created
        assertTrue(Files.exists(Paths.get("avatars/" + uuid + ".png")));

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
