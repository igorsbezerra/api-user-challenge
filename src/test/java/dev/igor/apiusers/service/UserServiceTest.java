package dev.igor.apiusers.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.igor.apiusers.adapter.PasswordAdapter;
import dev.igor.apiusers.api.request.UserRequest;
import dev.igor.apiusers.api.response.UserResponse;
import dev.igor.apiusers.error.UserAlreadyExistException;
import dev.igor.apiusers.error.UserNotFoundException;
import dev.igor.apiusers.model.User;
import dev.igor.apiusers.repository.UserRepository;
import dev.igor.apiusers.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private PasswordAdapter passwordAdapter;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void should_be_able_to_create_a_user_by_invoking_the_createUser_method() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";
        final var expectedAddress = "address";
        final var expectedPassword = "pass";
        final var expectedDate = LocalDateTime.now();

        User userModel = createUser(expectedId, expectedName, expectedDocument, expectedAddress, expectedPassword, expectedDate);
        UserRequest userRequest = createUserRequest(expectedName, expectedDocument, expectedAddress, expectedPassword);
        Mockito.when(userRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(passwordAdapter.encode(Mockito.anyString())).thenReturn("hash_pass");
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(userModel);

        UserResponse user = userService.createUser(userRequest);

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(expectedName, user.getName());
        Assertions.assertEquals(expectedDocument, user.getDocument());
    }

    @Test
    void should_not_be_able_to_create_a_user_by_invoking_the_createUser_method() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";
        final var expectedAddress = "address";
        final var expectedPassword = "pass";
        final var expectedDate = LocalDateTime.now();

        User userModel = createUser(expectedId, expectedName, expectedDocument, expectedAddress, expectedPassword, expectedDate);
        UserRequest userRequest = createUserRequest(expectedName, expectedDocument, expectedAddress, expectedPassword);
        Mockito.when(userRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.of(userModel));

        Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.createUser(userRequest));
    }

    @Test
    void should_be_able_find_user_by_document_when_invoking_the_findByDocument_method() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";
        final var expectedAddress = "address";
        final var expectedPassword = "pass";
        final var expectedDate = LocalDateTime.now();
        User userModel = createUser(expectedId, expectedName, expectedDocument, expectedAddress, expectedPassword, expectedDate);
        Mockito.when(userRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.of(userModel));

        UserResponse result = userService.findUserByDocument(expectedDocument);

        Assertions.assertEquals(expectedId, result.getId());
        Assertions.assertEquals(expectedName, result.getName());
        Assertions.assertEquals(expectedDocument, result.getDocument());
    }

    @Test
    void should_not_be_able_find_user_by_document_when_invoking_the_findByDocument_method() {
        Mockito.when(userRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserByDocument("doc"));
    }

    private User createUser(String id, String name, String document, String address, String password, LocalDateTime date) {
        return new User(id, name, document, address, password, date);
    }
    private UserRequest createUserRequest(String name, String document, String address, String password) {
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setDocument(document);
        userRequest.setAddress(address);
        userRequest.setPassword(password);
        return userRequest;
    }
}
