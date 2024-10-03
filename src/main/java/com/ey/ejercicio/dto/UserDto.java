package com.ey.ejercicio.dto;

import com.ey.util.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDto {
	
	private Long id;
	
	@NotEmpty(message = "Debe completar el nombre")	
	@Size(min = 8, max = 50)
	private String username;

	@NotEmpty(message = "Debe completar el email")
	@Email(message = "El formato del email no es válido")
	@Size(max = 150)
	private String email;
	
	@NotEmpty(message = "Debe completar el password")
	@Size(min = 8, max = 150)
	@ValidPassword
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
