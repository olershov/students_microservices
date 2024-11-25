package com.example.user.model.dto;

import com.example.user.validation.PasswordMatches;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@PasswordMatches
@Getter
public class UserRegDto {

    @NotNull
    @NotEmpty
    @JsonProperty("username")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "The password must include large and small characters of the English alphabet, numbers and be no shorter than 8 characters")
    @JsonProperty("password")
    private String password;

    @JsonProperty("confirm_password")
    private String confirmPassword;
}
