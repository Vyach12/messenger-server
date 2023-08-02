package com.chatty.authentication.services;

import com.chatty.authentication.models.User;
import com.chatty.authentication.repositories.UserRepository;
import com.chatty.authentication.util.dto.errors.logic.ErrorCode;
import com.chatty.authentication.util.exceptions.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> UserNotFoundException.builder()
                        .errorCode(ErrorCode.USER_NOT_FOUND)
                        .errorDate(LocalDateTime.now())
                        .dataCausedError(username)
                        .errorMessage("User with username = " + username + " does not exist")
                        .build()
                );
    }

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isEnabled(String username) {
        return findByUsername(username).isEnabled();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void changeEnabled(String username) {
        User user = findByUsername(username);
        user.setEnabled(!user.isEnabled());
        save(user);
    }
}
