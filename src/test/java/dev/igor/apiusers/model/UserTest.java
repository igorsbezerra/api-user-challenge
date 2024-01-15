package dev.igor.apiusers.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.igor.apiusers.api.request.UserRequest;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Test
    @DisplayName("Create empty user")
    void should_create_a_user_when_invoked_empty_constructor() {
        User user = new User();

        Assertions.assertNotNull(user);
    }

    @Test
    @DisplayName("Create user")
    void should_create_a_user_when_invoked_constructor_with_parameters() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";
        final var expectedAddress = "address";
        final var expectedPassword = "123456";
        final var now = LocalDateTime.now();

        User user = new User(expectedId, expectedName, expectedDocument, expectedAddress, expectedPassword, now);
        user.setId(expectedId);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(expectedId, user.getId());
        Assertions.assertEquals(expectedName, user.getName());
        Assertions.assertEquals(expectedDocument, user.getDocument());
        Assertions.assertEquals(expectedAddress, user.getAddress());
        Assertions.assertEquals(expectedPassword, user.getPassword());
        Assertions.assertEquals(now, user.getCreatedAt());
    }

    @Test
    @DisplayName("Create user")
    void should_create_a_user_when_invoked_create_method() {
        final var expectedName = "name";
        final var expectedDocument = "document";
        final var expectedAddress = "address";
        final var expectedPassword = "123456";

        UserRequest request = new UserRequest();
        request.setName(expectedName);
        request.setDocument(expectedDocument);
        request.setAddress(expectedAddress);
        request.setPassword(expectedPassword);

        final var user = User.create(request);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(expectedName, user.getName());
        Assertions.assertEquals(expectedDocument, user.getDocument());
        Assertions.assertEquals(expectedAddress, user.getAddress());
        Assertions.assertEquals(expectedPassword, user.getPassword());
        Assertions.assertNotNull(user.getCreatedAt());
    }
}
