package dev.igor.apiusers.service.impl;

import dev.igor.apiusers.adapter.PasswordAdapter;
import dev.igor.apiusers.api.request.UserRequest;
import dev.igor.apiusers.api.response.UserResponse;
import dev.igor.apiusers.error.UserAlreadyExistException;
import dev.igor.apiusers.error.UserNotFoundException;
import dev.igor.apiusers.model.User;
import dev.igor.apiusers.repository.UserRepository;
import dev.igor.apiusers.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordAdapter passwordAdapter;
    private final UserRepository repository;

    public UserServiceImpl(PasswordAdapter passwordAdapter, UserRepository repository) {
        this.passwordAdapter = passwordAdapter;
        this.repository = repository;
    }

    @Override
    public UserResponse createUser(final UserRequest request) {
        Optional<User> userExists = this.findByDocument(request.getDocument());
        if (userExists.isPresent()) {
            throw new UserAlreadyExistException();
        }
        request.setPassword(passwordAdapter.encode(request.getPassword()));
        User user = User.create(request);
        repository.save(user);
        return UserResponse.of(user);
    }

    @Override
    public UserResponse findUserByDocument(String document) {
        Optional<User> user = this.findByDocument(document);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return UserResponse.of(user.get());
    }

    private Optional<User> findByDocument(String document) {
        return repository.findByDocument(document);
    }
}
