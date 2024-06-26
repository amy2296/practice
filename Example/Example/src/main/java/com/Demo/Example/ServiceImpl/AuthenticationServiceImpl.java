package com.Demo.Example.ServiceImpl;

import com.Demo.Example.DTO.RegisterDTO;
import com.Demo.Example.Exception.UserAlreadyExistsException;
import com.Demo.Example.Model.User;
import com.Demo.Example.Repository.UserRepository;
import com.Demo.Example.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signup(RegisterDTO registerDTO) {
        Optional<User> existingUserByEmail = userRepository.findByEmail(registerDTO.getEmail());
        Optional<User> existingUserByUsername = userRepository.findByUsername(registerDTO.getUsername());

        if (existingUserByEmail.isPresent()) {
            // User already exists with the provided email, handle accordingly
            throw new UserAlreadyExistsException("User with this email already exists.");
        }

        if (existingUserByUsername.isPresent()) {
            // User already exists with the provided username, handle accordingly
            throw new UserAlreadyExistsException("User with this username already exists.");
        }


        // Create a new user
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword())); // Encrypt the password
        user.setEmail(registerDTO.getEmail());
        user.setAge(registerDTO.getAge());
        user.setAddress(registerDTO.getAddress());
        user.setRole(registerDTO.getRole());
        return userRepository.save(user);
    }

    @Override
    public boolean authenticate(String username, String password) {
        // Retrieve user by username
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the provided password matches the stored encrypted password
        return passwordEncoder.matches(password, user.getPassword());
    }

}
