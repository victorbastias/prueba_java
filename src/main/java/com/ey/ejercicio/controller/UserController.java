package com.ey.ejercicio.controller;

import com.ey.ejercicio.dto.UserDto;
import com.ey.ejercicio.model.User;
import com.ey.ejercicio.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
		
		return new ResponseEntity<>(lstUsersDto, HttpStatus.OK);
	}
}
