package com.Demo.Example.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.parameters.P;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;
    @Column(name = "Age")
    private String age;
    @Column(name = "Address")
    private String address;
    @Column(name = "Role")
    private String role;
    @OneToOne
    private ResetPassword resetPassword;

}