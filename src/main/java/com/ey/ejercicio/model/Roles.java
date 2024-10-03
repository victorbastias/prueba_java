package com.ey.ejercicio.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 60)
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Roles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Roles(String name) {
		this.name = name;
	}

}
