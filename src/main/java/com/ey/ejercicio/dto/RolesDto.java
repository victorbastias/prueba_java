package com.ey.ejercicio.dto;

import javax.validation.constraints.Size;

public class RolesDto {
	private String name;

	@Size(max = 60)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
