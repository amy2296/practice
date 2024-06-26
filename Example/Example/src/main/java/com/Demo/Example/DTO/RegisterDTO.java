package com.Demo.Example.DTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterDTO {
    private int id;
    private String username;
    private String password;
    private String email;
    private String age;
    private String address;
    private String Role;
}