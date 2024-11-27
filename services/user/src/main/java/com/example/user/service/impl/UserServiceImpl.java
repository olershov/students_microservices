package com.example.user.service.impl;

import com.example.user.exceptions.UserAlreadyExistsException;
import com.example.user.exceptions.UserNotFoundException;
import com.example.user.model.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void regUser(User user) {
        var existedUser = userRepository.findByUsername(user.getUsername());
        if (existedUser.isPresent()) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}
