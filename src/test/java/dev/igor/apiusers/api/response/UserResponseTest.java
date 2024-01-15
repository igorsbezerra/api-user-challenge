package dev.igor.apiusers.api.response;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.igor.apiusers.model.User;

@ExtendWith(MockitoExtension.class)
public class UserResponseTest {
    @Test
    @DisplayName("Create user response")
    void should_create_a_user_when_invoked_empty_constructor() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";

        UserResponse user = new UserResponse(expectedId, expectedName, expectedDocument);
        user.setName(expectedName);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(expectedId, user.getId());
        Assertions.assertEquals(expectedName, user.getName());
        Assertions.assertEquals(expectedDocument, user.getDocument());
    }

    @Test
    @DisplayName("Create user response with method of")
    void should_covert_a_user_response_when_invoked_method_of() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";
        final var expectedAddress = "address";
        final var expectedPassword = "123456";
        final var now = LocalDateTime.now();
        User user = new User(expectedId, expectedName, expectedDocument, expectedAddress, expectedPassword, now);

        final var response = UserResponse.of(user);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(expectedId, response.getId());
        Assertions.assertEquals(expectedName, response.getName());
        Assertions.assertEquals(expectedDocument, response.getDocument());
    }
}
