package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserLoginDTO {
    @NotBlank
    private String emailOrUsername;

    @NotBlank
    private String password;

    public boolean isEmail() {
        return emailOrUsername.matches("^[\\w-\\.]+@([\\w]+\\.)+[\\w-]+$");
    }
}
