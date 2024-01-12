package dev.igor.apiusers.service;

import dev.igor.apiusers.api.request.UserRequest;
import dev.igor.apiusers.api.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse findUserByDocument(String document);
}
