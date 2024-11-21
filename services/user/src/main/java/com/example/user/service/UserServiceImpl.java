package com.example.user.service;

import com.example.user.exceptions.UserAlreadyExistsException;
import com.example.user.model.dto.UserRegDto;
import com.example.user.model.entity.UserEntity;
import com.example.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(UserRegDto userDto) {
        var existedUser = userRepository.findByUsername(userDto.getUsername());
        if (existedUser.isPresent()) {
            throw new UserAlreadyExistsException("User with username " + userDto.getUsername() + " already exists");
        }
        var userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userRepository.save(userEntity);
    }
}
