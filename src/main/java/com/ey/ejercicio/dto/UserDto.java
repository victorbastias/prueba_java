package com.ey.ejercicio.dto;

import java.util.List;

public class UserDto {
	
	private Long idUser;		
	private String name;
	private String email;
	private String password;	
    private List<PhonesDto> phones;
	
    public Long getIdUser() {
		return idUser;
	}
    
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
