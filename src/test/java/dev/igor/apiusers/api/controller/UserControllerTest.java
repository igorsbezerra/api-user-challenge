package dev.igor.apiusers.api.controller;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.igor.apiusers.api.request.UserRequest;
import dev.igor.apiusers.api.response.UserResponse;
import dev.igor.apiusers.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService service;

    @InjectMocks
    private UserController userController;

    @Test
    void should_be_able_to_create_a_user_by_invoking_the_createUser_method() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";
        final var expectedAddress = "address";
        final var expectedPassword = "123456";
        UserRequest userRequest = createUserRequest(expectedName, expectedDocument, expectedAddress, expectedPassword);
        UserResponse userResponse = createUserResponse(expectedId, expectedName, expectedDocument);
        Mockito.when(service.createUser(Mockito.any())).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.createUser(userRequest);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(expectedId, response.getBody().getId());
        Mockito.verify(service, Mockito.times(1)).createUser(userRequest);
    }

    @Test
    void should_be_able_find_user_by_document_when_invoking_the_findByDocument_method() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedDocument = "10274";
        final var expectedName = "name";
        UserResponse userResponse = createUserResponse(expectedId, expectedName, expectedDocument);
        Mockito.when(service.findUserByDocument(Mockito.anyString())).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.findByDocument(expectedDocument);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedId, response.getBody().getId());
        Mockito.verify(service, Mockito.times(1)).findUserByDocument(expectedDocument);
    }

    private UserRequest createUserRequest(String expectedName, String expectedDocument, String expectedAddress, String expectedPassword) {
        UserRequest request = new UserRequest();
        request.setName(expectedName);
        request.setDocument(expectedDocument);
        request.setAddress(expectedAddress);
        request.setPassword(expectedPassword);
        return request;
    }

    private UserResponse createUserResponse(String expectedId, String expectedName, String expectedDocument) {
        UserResponse response = new UserResponse(expectedId, expectedName, expectedDocument);
        return response;
    }
}
