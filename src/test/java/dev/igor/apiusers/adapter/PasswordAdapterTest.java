package dev.igor.apiusers.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordAdapterTest {
    @InjectMocks
    private PasswordAdapter passwordAdapter;

    @Test
    void should_hash_password_when_use_lib_bcrypt() {
        final var expectedPassword = "password";

        String encodedPassword = passwordAdapter.encode(expectedPassword);

        Assertions.assertNotNull(encodedPassword);
    }

    @Test
    void should_match_hash_password_when_use_lib_bcrypt() {
        final var expectedPassword = "password";
        String encodedPassword = passwordAdapter.encode(expectedPassword);

        boolean result = passwordAdapter.match(encodedPassword, expectedPassword);

        Assertions.assertTrue(result);
    }

    @Test
    void should_not_match_hash_password_when_use_lib_bcrypt() {
        final var expectedPassword = "password";
        String encodedPassword = passwordAdapter.encode(expectedPassword);

        boolean result = passwordAdapter.match(encodedPassword, "incorrect password");

        Assertions.assertFalse(result);
    }
}
