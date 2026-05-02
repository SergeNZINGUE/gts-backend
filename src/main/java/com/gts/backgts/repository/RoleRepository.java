package com.gts.backgts.repository;

import com.gts.backgts.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByCodeTypeRole(String codeTypeRole);
}
