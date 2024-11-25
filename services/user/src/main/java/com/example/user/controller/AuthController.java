package com.example.user.controller;

import com.example.user.model.dto.UserAuthDto;
import com.example.user.model.dto.UserRegDto;
import com.example.user.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.user.controller.EndpointConstants.*;

@AllArgsConstructor
@RestController
@RequestMapping(AUTH)
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(REG)
    public ResponseEntity<?> regUser(@Valid @RequestBody UserRegDto regDto) {
        var result = authenticationService.signUp(regDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody UserAuthDto authDto) {
        var result = authenticationService.signIn(authDto);
        return ResponseEntity.ok(result);
    }
}
