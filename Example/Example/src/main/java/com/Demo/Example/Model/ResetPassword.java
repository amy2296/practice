package com.Demo.Example.Model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPassword {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
private Long rid;
    @Column(nullable = false)
private Integer otp;
    @Column(nullable = false)
private Date expirationTime;
@OneToOne
private User user;

}
