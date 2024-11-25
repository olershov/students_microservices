package com.example.user.service;

import com.example.user.exceptions.UserAlreadyExistsException;
import com.example.user.model.dto.UserRegDto;
import com.example.user.model.entity.UserEntity;
import com.example.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void regUser(UserRegDto userDto) {
        var existedUser = userRepository.findByUsername(userDto.getUsername());
        if (existedUser.isPresent()) {
            throw new UserAlreadyExistsException("User with username " + userDto.getUsername() + " already exists");
        }
        var userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
