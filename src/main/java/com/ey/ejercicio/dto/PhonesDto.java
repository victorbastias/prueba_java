package com.ey.ejercicio.dto;

import javax.validation.constraints.Size;

public class PhonesDto {

	@Size(max = 10)
	private String number;

	@Size(max = 5)
	private String citycode;

	@Size(max = 2)
	private String countrycode;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
