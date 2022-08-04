package com.ey.ejercicio.services;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ey.ejercicio.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	//para pasar parametro ? y la posición del parametro, en este casa es el parametro 1
	
	@Query(value = "Select count(*) FROM User Where email = ?1")
	Integer getExistEmail(String mail);
	
	@Query(value = "Select id_user, email, name, is_active, last_login, created, last_update, password FROM User Where email = ?1 and password = ?2", nativeQuery = true)
	List<User> getUserByEmailPass(String mail, String pass);
	
	@Query(value = "Select top 1 id_user FROM User Where email = ?1 and password = ?2 and name = ?3", nativeQuery = true)
	Long getIdUserByEmailPassName(String mail, String pass, String name);
	//@Query(value = "Select Top 1 * FROM User Where password = '?1'")
	//User getByPassword(String pass);	
}

