package dev.igor.apiusers.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    @Size(min = 11, max = 14)
    private String document;
    @NotEmpty
    private String address;
    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;
}
