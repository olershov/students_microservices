package com.example.user.controller;

import com.example.user.model.dto.UserAuthDto;
import com.example.user.model.dto.UserRegDto;
import com.example.user.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import static com.example.user.controller.EndpointConstants.*;

@AllArgsConstructor
@RestController
@RequestMapping(AUTH)
public class AuthController {

    private final AuthenticationService authenticationService;
    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    @PostMapping(REG)
    public ResponseEntity<?> regUser(@Valid @RequestBody UserRegDto regDto) {
        LOGGER.info("Request to register a new user with username: " + regDto.getUsername());
        var result = authenticationService.signUp(regDto);
        LOGGER.info(regDto.getUsername() + ": successful registration");
        return ResponseEntity.ok(result);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody UserAuthDto authDto) {
        LOGGER.info("User authentication request: " + authDto.getUsername());
        var result = authenticationService.signIn(authDto);
        LOGGER.info(authDto.getUsername() + ": successful authentication");
        return ResponseEntity.ok(result);
    }
}
