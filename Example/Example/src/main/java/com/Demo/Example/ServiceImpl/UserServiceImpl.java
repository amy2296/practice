package com.Demo.Example.ServiceImpl;

import com.Demo.Example.DTO.RegisterDTO;
import com.Demo.Example.Model.User;
import com.Demo.Example.Repository.UserRepository;
import com.Demo.Example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User createUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setAddress(registerDTO.getAddress());
        user.setAge(registerDTO.getAge());
        user.setRole(registerDTO.getRole());

        return userRepository.save(user);
    }

    @Override
    public User add(User user) {
        // Implement logic to add user
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public User update(User user) {
        // Implement logic to update user
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public void save(User user) {
        userRepository.save(user);
    }


}

