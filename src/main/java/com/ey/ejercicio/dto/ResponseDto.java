package com.ey.ejercicio.dto;

public class ResponseDto {
	
	private Long id;
	private String created;
	private String modified;
	private String last_login;
	private String Token;
	private Boolean isactive;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCreated() {
		return created;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	
	public String getModified() {
		return modified;
	}
	
	public void setModified(String modified) {
		this.modified = modified;
	}
	
	public String getLast_login() {
		return last_login;
	}
	
	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}
	
	public String getToken() {
		return Token;
	}
	
	public void setToken(String token) {
		Token = token;
	}
	
	public Boolean getIsactive() {
		return isactive;
	}
	
	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

}
