package dev.igor.apiusers.model;

import dev.igor.apiusers.api.request.UserRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "document", length = 14, nullable = false)
    private String document;
    @Column(name = "address", nullable = false, unique = true)
    private String address;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static User create(final UserRequest request) {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        return User.builder()
                .id(id)
                .name(request.getName())
                .document(request.getDocument())
                .address(request.getAddress())
                .password(request.getPassword())
                .createdAt(now)
                .build();
    }
}
