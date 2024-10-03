package com.ey.ejercicio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.ejercicio.model.Roles;


public interface RolesRepository extends JpaRepository<Roles, Long>{

	Optional<Roles> findByName(String name);
	
}
