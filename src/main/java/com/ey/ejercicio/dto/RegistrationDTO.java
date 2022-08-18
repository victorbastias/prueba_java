package com.ey.ejercicio.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegistrationDTO {
	
	@NotEmpty(message = "El nombew no debe ser vacío")	
	@Size(min = 8, message = "EL nombre de usuario debe tener un mínimo de 8 carácteres")
	private String username;
	
	@NotEmpty(message = "El email no debe ser vacío")	
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 8, message = "La contraseña debe tener un mínimo de 8 carácteres")
	private String password;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}  

	public void setPassword(String password) {
		this.password = password;
	}

	public RegistrationDTO() {
		super();
	}

}
