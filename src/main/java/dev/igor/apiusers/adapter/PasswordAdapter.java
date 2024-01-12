package dev.igor.apiusers.adapter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordAdapter {
    public String encode(final String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    public boolean match(final String passwordHash, final String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), passwordHash);
        return result.verified;
    }
}
