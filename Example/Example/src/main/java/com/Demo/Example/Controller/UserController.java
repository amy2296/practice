package com.Demo.Example.Controller;
import com.Demo.Example.DTO.ChangePasswordDTO;
import com.Demo.Example.DTO.LoginDTO;
import com.Demo.Example.DTO.RegisterDTO;
import com.Demo.Example.Model.JwtResponse;
import com.Demo.Example.Model.User;
import com.Demo.Example.Service.AuthenticationService;
import com.Demo.Example.Service.JwtService;
import com.Demo.Example.Service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class UserController{
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/Signup")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            User registeredUser = authenticationService.signup(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Extract username and password from DTO
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // Authenticate user
        boolean isAuthenticated = authenticationService.authenticate(username, password);

        if (isAuthenticated) {
            // Generate JWT token
            String token = jwtService.generateToken(username);

            // Return JWT token and email in response
            JwtResponse jwtResponse = new JwtResponse(token);
            return ResponseEntity.ok(jwtResponse);
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}