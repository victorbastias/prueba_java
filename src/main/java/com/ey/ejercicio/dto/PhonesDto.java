package com.ey.ejercicio.dto;

public class PhonesDto {
	private String nombre;
	private String citycode;
	private String countrycode;
	
	public String getNombre() {
		return nombre;
	}
	public void setNumbre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getCitycode() {
		return citycode;
	}
	
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	
	public String getCountrycode() {
		return countrycode;
	}
	
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

}
