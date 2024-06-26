package com.Demo.Example.Service;

import com.Demo.Example.DTO.RegisterDTO;
import com.Demo.Example.Model.User;


public interface AuthenticationService {
    User signup(RegisterDTO registerDTO);
    boolean authenticate(String username, String password);

}
