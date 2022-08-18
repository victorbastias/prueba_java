package com.ey.ejercicio.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginDTO {
	
	@NotEmpty
	private String usernameOrEmail;
	
	@NotEmpty
	@Size(min = 8, message = "La contraseña debe tener un mínimo de 8 carácteres")
	private String password;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
