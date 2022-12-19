package com.digitalbook.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbook.user.model.Role;

public interface RoleRepository extends JpaRepository<Role,String> {

	String findByRoleName(String roleName);
}
