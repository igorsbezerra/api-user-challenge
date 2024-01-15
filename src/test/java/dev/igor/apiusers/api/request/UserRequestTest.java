package dev.igor.apiusers.api.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRequestTest {
    @Test
    @DisplayName("Create user request")
    void should_create_user_request_when_instantite_class() {
        final var expectedName = "name";
        final var expectedDocument = "document";
        final var expectedAddress = "address";
        final var expectedPassword = "123456";

        UserRequest request = new UserRequest();
        request.setName(expectedName);
        request.setDocument(expectedDocument);
        request.setAddress(expectedAddress);
        request.setPassword(expectedPassword);

        Assertions.assertEquals(expectedName, request.getName());
        Assertions.assertEquals(expectedDocument, request.getDocument());
        Assertions.assertEquals(expectedAddress, request.getAddress());
        Assertions.assertEquals(expectedPassword, request.getPassword());
    }
}
