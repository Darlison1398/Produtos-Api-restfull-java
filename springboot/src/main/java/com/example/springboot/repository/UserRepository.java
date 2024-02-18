package com.example.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.springboot.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByLogin(String login);
    
}
