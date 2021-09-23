package com.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	

}
