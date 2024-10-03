package com.ey.ejercicio.model;

import javax.persistence.*;

@Entity
@Table(name="phones",
		uniqueConstraints = {
		@UniqueConstraint(columnNames = "id") })
public class Phone {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id_phone;	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="id", referencedColumnName="id", nullable = false)
	private User user;

	@Column(name = "number", length = 10)
	private String number;

	@Column(name = "citycode", length = 5)
	private String citycode;

	@Column(name = "countrycode", length = 2)
	private String countrycode;

	public int getId_phone() {
		return id_phone;
	}

	public void setId_phone(int id_phone) {
		this.id_phone = id_phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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