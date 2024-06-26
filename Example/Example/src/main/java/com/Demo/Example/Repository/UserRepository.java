package com.Demo.Example.Repository;

import com.Demo.Example.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    void deleteByEmail(String email);

    Optional<User> findByEmail(String username);


    @Modifying
    @Transactional
    @Query("update User u set u.password = ?2 where u.email = ?1")
    void updatePassword( String username, String password);

    Optional<User> findByUsername(String username);
}





