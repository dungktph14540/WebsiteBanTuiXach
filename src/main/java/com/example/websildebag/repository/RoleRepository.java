package com.example.websildebag.repository;

import com.example.websildebag.entity.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByRoleName(String rolename);

    @Query(value = "SELECT role_name FROM user_role ur " +
            "INNER JOIN roles r ON r.role_id = ur.role_id " +
            "INNER JOIN users u ON ur.user_id = u.user_id " +
            "WHERE u.username = ?", nativeQuery = true)
    List<String> getRoleName(String username);

}
