package com.example.user.controller;

import com.example.user.model.dto.UserRegDto;
import com.example.user.model.entity.UserEntity;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.user.controller.EndpointConstants.*;

@AllArgsConstructor
@RestController
@RequestMapping(USER_SERVICE)
public class UserController {

    private final UserService userService;

    @PostMapping(REG)
    public ResponseEntity<?> regUser(@Valid @RequestBody UserRegDto userDto,
                                     @RequestHeader(name = "Authorization") String jwtToken) {
        userService.save(userDto);
        return ResponseEntity.ok().build();
    }

//    @PostMapping(LOGIN)
//    public ResponseEntity<?> login(@Valid @RequestBody UserEntity user,
//                                   @RequestHeader(name = "Authorization") String jwtToken) {
//
//    }
//
//    @PostMapping(LOGOUT)
//    public ResponseEntity<?> logout(@Valid @RequestBody UserEntity user,
//                                    @RequestHeader(name = "Authorization") String jwtToken) {
//
//    }


}
