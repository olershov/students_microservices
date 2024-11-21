package com.example.user.model.dto;

import com.example.user.validation.PasswordMatches;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@PasswordMatches
@Getter
public class UserRegDto {

    @NotNull
    @NotEmpty
    @JsonProperty("username")
    private String username;

    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Пароль должен включать в себя большие и малые символы английского алфавита, цифры и быть не короче 8 символов")
    @JsonProperty("password")
    private String password;

    @JsonProperty("confirm_password")
    private String confirmPassword;
}
