package com.ey.ejercicio.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name="user",
		uniqueConstraints = { 
		@UniqueConstraint(columnNames = "name"),
		@UniqueConstraint(columnNames = "email") })
public class User{
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long idUser;
	@Column(name = "name", length = 50)	
	private String name;	
	
	
	@Column(name = "email", length = 150)
	private String email;
	
	@Column(name = "password", length = 50)
	private String password;
	
	@Column(name = "created", length = 20)
	private String created;
	
	@Column(name = "last_login", length = 20)
	private String last_login;
	
	@Column(name = "last_update", length = 20)
	private String last_update;
	
	@Column(name = "is_active")
	private Boolean is_active;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, targetEntity = Phone.class)
	@JoinTable(	name = "phones", 
	joinColumns = @JoinColumn(name = "user_id") 
	)
    private Phone phones;	

	
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


	public String getCreated() {
		return created;
	}


	public void setCreated(String created) {
		this.created = created;
	}

	public String getLast_login() {
		return last_login;
	}


	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}
	
	public String getLast_update() {
		return last_update;
	}


	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	
	public Boolean getIs_active() {
		return is_active;
	}


	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public Phone getPhones() {
		return phones;
	}


	public void setPhones(Phone phones) {
		this.phones = phones;
	}	
}
