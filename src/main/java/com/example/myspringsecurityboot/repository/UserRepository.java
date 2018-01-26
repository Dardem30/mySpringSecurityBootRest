package com.example.myspringsecurityboot.repository;

import com.example.myspringsecurityboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    User getUserByUsername(String username);
}
