package com.ey.ejercicio.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDto {
	
	private Long id;
	
	@NotEmpty(message = "Debe completar el nombre")	
	@Size(min = 8, message = "EL nombre de usuario debe tener un mínimo de 8 carácteres")
	private String username;

	@NotEmpty(message = "Debe completar el email")
	@Email
	private String email;
	
	@NotEmpty(message = "Debe completar el password")
	private String password;
	
    private List<PhonesDto> phones;
	
    public Long getId() {
		return id;
	}
    
	public void setId(Long id) {
		this.id = id;
	}
	
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
	
	public List<PhonesDto> getPhones() {
		return phones;
	}
	
	public void setPhones(List<PhonesDto> phones) {
		this.phones = phones;
	}	

}
