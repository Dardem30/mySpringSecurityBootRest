package com.example.myspringsecurityboot.repository;

import com.example.myspringsecurityboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{
}
