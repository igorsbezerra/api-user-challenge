package dev.igor.apiusers.api.response;

import dev.igor.apiusers.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String document;

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getDocument());
    }
}
