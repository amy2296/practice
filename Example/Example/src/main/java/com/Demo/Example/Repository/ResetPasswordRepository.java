package com.Demo.Example.Repository;

import com.Demo.Example.Model.ResetPassword;
import com.Demo.Example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword,Integer>{

    @Query("select fp from ResetPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ResetPassword>findbyOtpandUser(Integer Otp, User user);


    void deleteAllByRid(Long rid);
}
