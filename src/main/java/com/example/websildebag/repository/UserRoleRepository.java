package com.example.websildebag.repository;

import com.example.websildebag.entity.User_Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<User_Role,Long> {
    Optional<User_Role> findByUsers_Username(String username);
}
