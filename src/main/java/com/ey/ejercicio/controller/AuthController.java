package com.ey.ejercicio.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.ejercicio.security.JwtTokenProvider;
import com.ey.ejercicio.dto.JWTAuthResonseDTO;
import com.ey.ejercicio.dto.LoginDTO;
import com.ey.ejercicio.dto.MensajeDto;
import com.ey.ejercicio.dto.PhonesDto;
import com.ey.ejercicio.dto.RegistrationDTO;
import com.ey.ejercicio.dto.ResponseDto;
import com.ey.ejercicio.dto.UserDto;
import com.ey.ejercicio.model.Phone;
import com.ey.ejercicio.model.Roles;
import com.ey.ejercicio.model.User;
import com.ey.ejercicio.repository.RolesRepository;
import com.ey.ejercicio.repository.UserRepository;


@RestController
@RequestMapping("/api/auth")
public class AuthController {	
	
	@Autowired
	private AuthenticationManager authenticationManagerBean;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RolesRepository roleRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;	
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@PostMapping("/v1/login")
	public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@Valid @RequestBody LoginDTO loginDTO){
		Authentication authentication = authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//obtenemos el token del jwtTokenProvider
		String token = jwtTokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTAuthResonseDTO(token));
	}
	
	@PostMapping("/v1/register")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDto userDto){
		ResponseDto response = new ResponseDto();
		MensajeDto mensaje = new MensajeDto();
		if(userRepository.existsByEmail(userDto.getEmail())) {
			mensaje.setMensaje("Ese email de usuario ya existe");
			return new ResponseEntity<Object>(mensaje, HttpStatus.BAD_REQUEST);
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();  
		String fechaActual = formatter.format(date);
		
		User user = modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setIsactive(true);
		user.setCreated(fechaActual);		
		user.setPhones(new ArrayList<Phone>());
		
		Optional<Roles> roles = roleRepositorio.findByName("NORMAL");
		
		//como es una base de datos en memoria, si no existe el rol se crea
		if(roles.isPresent()) {
			user.setRoles(Collections.singleton(roles.get()));
		}else {
			Roles tmpRol = new Roles();
			tmpRol.setName("NORMAL");
			roleRepositorio.save(tmpRol);
			user.setRoles(Collections.singleton(tmpRol));
		}		
		
		List<Phone> lstPhones = modelMapper.map(userDto.getPhones(), new TypeToken<List<Phone>>(){}.getType());
		
		for (Phone phone : lstPhones) {
			user.addToPhoneList(phone);
		}		
		
		userRepository.save(user);
		
		response = modelMapper.map(user, ResponseDto.class);
		
		Authentication authentication = authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		response.setToken(jwtTokenProvider.generateToken(authentication));
		
	
		mensaje.setMensaje("Usuario registrado exitosamente");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	
}
