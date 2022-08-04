package com.ey.ejercicio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="phones")
public class Phone {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id_phone;
	
	@Column(name = "id_user")
	private int id_user;
	
	@Column(name = "number", length = 10)
	private String number;
	
	@Column(name = "citycode", length = 5)
	private String citycode;
	
	@Column(name = "contrycode", length = 2)
	private String contrycode;
	
	public int getIdPhone() {
		return id_phone;
	}


	public void setIdPhone(int id_phone) {
		this.id_phone = id_phone;
	}

		
	public int getIdUser() {
		return id_user;
	}

	public void setIdUser(int id_user) {
		this.id_user = id_user;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getCitycode() {
		return citycode;
	}

	public void setCityCode(String citycode) {
		this.citycode = citycode;
	}
	
	public String getContrycode() {
		return contrycode;
	}

	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}

}
