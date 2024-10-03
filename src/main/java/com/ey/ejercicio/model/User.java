package com.ey.ejercicio.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="user",
		uniqueConstraints = { 
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User{
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	@Column(name = "username", length = 50)	
	private String username;
	
	@Column(name = "email", length = 150)
	private String email;

	@Column(name = "password", length = 250)
	private String password;
	
	@Column(name = "created", length = 30)
	private String created;

	@Column(name = "last_login", length = 30)
	private String last_login;
	
	@Column(name = "modified", length = 30)
	private String modified;
	
	@Column(name = "isactive")
	private Boolean isactive;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Roles> roles = new HashSet<>();

	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
    private List<Phone> phones;	

	
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
	
	public Boolean getIsactive() {
		return isactive;
	}


	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}


	public List<Phone> getPhones() {
		return phones;
	}


	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	
	 public void addToPhoneList(Phone phone) {
		 phone.setUser(this);
		 this.phones.add(phone);

	 }
	
	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> role) {
		this.roles = role;
	}
}
