package com.Demo.Example.Service;


import com.Demo.Example.DTO.RegisterDTO;
import com.Demo.Example.Model.User;

import java.util.Optional;

public interface UserService {

    User createUser(RegisterDTO registerDTO);

    User add(User user);

    Optional<User> getUser(String email);

    void delete(String email);

    User update(User user);

    Optional<User> findByEmail(String username);

    void save(User user);
}
