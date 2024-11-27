package com.example.user.service.interfaces;

import com.example.user.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void regUser(User user);
}
