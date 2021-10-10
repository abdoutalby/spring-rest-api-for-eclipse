package com.eclipse.devices.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eclipse.devices.model.Role;
import com.eclipse.devices.model.RoleName;
 

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}