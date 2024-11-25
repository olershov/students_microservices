package com.example.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAuthDto {

    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
