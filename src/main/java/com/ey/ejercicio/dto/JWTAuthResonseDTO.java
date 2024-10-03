package com.ey.ejercicio.dto;

public class JWTAuthResonseDTO {

	private String tokenAccess;
	private String tokenType = "Bearer";
	public String getTokenAccess() {
		return tokenAccess;
	}
	public void setTokenAccess(String tokenAccess) {
		this.tokenAccess = tokenAccess;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public JWTAuthResonseDTO(String tokenAccess, String tokenType) {
		super();
		this.tokenAccess = tokenAccess;
		this.tokenType = tokenType;
	}
	public JWTAuthResonseDTO(String tokenAccess) {
		super();
		this.tokenAccess = tokenAccess;
	}

    public String getToken() {
		return tokenAccess;
    }
}
