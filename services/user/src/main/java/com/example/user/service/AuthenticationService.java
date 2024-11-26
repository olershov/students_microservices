package com.example.user.service;

import com.example.user.model.dto.AuthResponse;
import com.example.user.model.dto.UserAuthDto;
import com.example.user.model.dto.UserRegDto;
import com.example.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param regDto данные пользователя
     * @return токен
     */
    public AuthResponse signUp(UserRegDto regDto) {
        var user =  User.builder()
                .username(regDto.getUsername())
                .password(passwordEncoder.encode(regDto.getPassword()))
                .build();
        userService.regUser(user);
        var jwt = jwtService.generateToken(user);
        return new AuthResponse(user.getUsername(), jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param authDto данные пользователя
     * @return токен
     */
    public AuthResponse signIn(UserAuthDto authDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authDto.getUsername(),
                authDto.getPassword()
        ));
        var user = userService.loadUserByUsername(authDto.getUsername());
        var jwt = jwtService.generateToken(user);
        return new AuthResponse(user.getUsername(), jwt);
    }
}
