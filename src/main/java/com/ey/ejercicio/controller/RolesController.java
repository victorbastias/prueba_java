package com.ey.ejercicio.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.ejercicio.dto.MensajeDto;
import com.ey.ejercicio.dto.RolesDto;
import com.ey.ejercicio.model.Roles;

import com.ey.ejercicio.repository.RolesRepository;

@RestController
@RequestMapping("/api/roles")
public class RolesController {
	
	@Autowired
	private RolesRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/v1/getAll")
	public ResponseEntity<Object> getAll(){		
		
		List<Roles> roles = roleRepository.findAll();		
		return new ResponseEntity<Object>(roles, HttpStatus.OK);
	}
	
	@PostMapping("/v1/save")
	public ResponseEntity<Object> save(@RequestBody RolesDto rolDto){		
		
		MensajeDto error = new MensajeDto();		
		Optional<Roles> roles = roleRepository.findByName(rolDto.getName());
		if(roles.isPresent()) {
			error.setMensaje("El rol "+ rolDto.getName() + " ya existe");
			return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST); 
		}
		Roles rol = modelMapper.map(rolDto, Roles.class);				
		roleRepository.save(rol);
		
		return new ResponseEntity<Object>(roles, HttpStatus.OK);
	}

}
