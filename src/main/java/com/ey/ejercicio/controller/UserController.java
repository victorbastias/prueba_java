package com.ey.ejercicio.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.ejercicio.dto.MensajeDto;
import com.ey.ejercicio.dto.ResponseDto;
import com.ey.ejercicio.dto.UserDto;
import com.ey.ejercicio.model.Phone;
import com.ey.ejercicio.model.Roles;
import com.ey.ejercicio.model.User;
import com.ey.ejercicio.repository.RolesRepository;
import com.ey.ejercicio.repository.UserRepository;
import com.ey.ejercicio.services.UserServices;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
		
	
	@GetMapping("/v1/getAll")
	public ResponseEntity<Object> getAll(){		
		
		List<User> lstUsers = userRepository.findAll();
		List<UserDto> lstUsersDto = modelMapper.map(lstUsers, new TypeToken<List<UserDto>>(){}.getType());
		
		return new ResponseEntity<Object>(lstUsersDto, HttpStatus.OK);
	}
}
