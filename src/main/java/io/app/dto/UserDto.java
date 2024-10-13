package io.app.dto;

import io.app.models.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private String userID;
    private String email;
    private String name;
    private String password;
    private Roles roles;
    private String profileImageUrl;
}
