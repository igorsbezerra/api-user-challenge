package dev.igor.apiusers.integration;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dev.igor.apiusers.model.User;
import dev.igor.apiusers.repository.UserRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIntegrationTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper mapper;
    @Autowired UserRepository userRepository;

    @Test
    void should_be_possible_to_run_an_integrated_test_to_create_a_user() throws Exception {
        String json = createUserJson();

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void should_not_be_possible_to_run_an_integrated_test_to_create_a_user_when_to_send_empty_value_properties() throws Exception {
        String json = createEmptyUserJson();

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void should_not_be_possible_to_run_an_integrated_test_to_create_a_user_when_not_to_send_body() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void should_not_be_possible_to_run_an_integrated_test_to_create_a_user_when_user_exists() throws Exception {
        String json = createUserJson();
        User user = new User(
            UUID.randomUUID().toString(),
            "Igor",
            "34686598650",
            "Sao Paulo",
            "123456",
            LocalDateTime.now()
        );
        userRepository.save(user);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private String createUserJson() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", "Igor");
        objectNode.put("document", "34686598650");
        objectNode.put("address", "Sao Paulo");
        objectNode.put("password", "123456");
        return mapper.writeValueAsString(objectNode);
    }

    private String createEmptyUserJson() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", "");
        objectNode.put("document", "");
        objectNode.put("address", "");
        objectNode.put("password", "");
        return mapper.writeValueAsString(objectNode);
    }
}
