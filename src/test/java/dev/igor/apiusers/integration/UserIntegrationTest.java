package dev.igor.apiusers.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
public class UserIntegrationTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper mapper;

    @Test
    void should_be_possible_to_run_an_integrated_test_to_create_a_user() throws Exception {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", "Igor");
        objectNode.put("document", "34686598650");
        objectNode.put("address", "Sao Paulo");
        objectNode.put("password", "123456");
        String json = mapper.writeValueAsString(objectNode);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void should_not_be_possible_to_run_an_integrated_test_to_create_a_user() throws Exception {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", "");
        objectNode.put("document", "");
        objectNode.put("address", "");
        objectNode.put("password", "");
        String json = mapper.writeValueAsString(objectNode);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
