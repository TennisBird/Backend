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

    protected boolean testWithCorrectUuid() {
        return true;
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

    protected String createPerson() throws Exception {
        return mockMvc.perform(post("/")
                        .content(parseJSONIntoString("api/create_person_request.json"))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
