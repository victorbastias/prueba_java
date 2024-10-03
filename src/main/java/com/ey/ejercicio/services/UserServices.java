package com.ey.ejercicio.services;

import com.ey.ejercicio.dto.ResponseDto;
import com.ey.ejercicio.dto.UserDto;
import com.ey.ejercicio.model.User;
import com.ey.ejercicio.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServices{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	public User getUsuario()  throws Exception {
		try {
			User user = new User();
			user.setUsername("Prueba");
			return user;
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}		
	}
	
	public Boolean setUpdateUsuario(UserDto entity)  throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			Date date = new Date();  
			String fechaActual = formatter.format(date);
			
			User user = modelMapper.map(entity, User.class);			
			user.setModified(fechaActual);
			user.setLast_login(fechaActual);			
			userRepository.save(user);
			return true;			
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}		
	}
	
	
	public ResponseDto setUsuario(UserDto user)  throws Exception {
		try {
		ResponseDto response = new ResponseDto();
		User userModel = new User();
		userModel.setUsername(user.getUsername());
		userModel.setEmail(user.getEmail());
		userModel.setPassword(user.getPassword());
		userModel.setIsactive(true);
		userModel.setPhones(null);		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();  
		String fechaActual = formatter.format(date);
		userModel.setCreated(fechaActual);
		
		userModel = userRepository.save(userModel);		
		userModel = userRepository.findById(userModel.getId()).get();
		
		response.setId(userModel.getId());
		response.setCreated(userModel.getCreated());
		response.setModified(userModel.getModified());		
		
		String last_login = userModel.getLast_login();
		if(last_login == null || last_login.trim().isEmpty()) {
			response.setLast_login(userModel.getCreated());	
		}
		else {
			response.setLast_login(userModel.getLast_login());	
		}
		
		response.setIsactive(userModel.getIsactive());		
		
		return response;
		//return userRepository.getByPassword(pass);
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}		
	}
	
	public Boolean getExistMail(String mail) throws Exception {
		try {		
			return userRepository.existsByEmail(mail);			
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
	}
	
	public Long getUserByEmailPassName(String mail, String password, String userName) throws Exception {
		try {
			Optional<User> user = userRepository.findByUsernameOrEmail(mail, userName);
			
			if(user.isPresent()) {
				return user.get().getId();
			}
			
			return  (long) 0;			
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
	}
	
	public ResponseDto getUserById(Long id_user) throws Exception {
		try {
			ResponseDto response = new ResponseDto();
			User user = userRepository.findById(id_user).get();
			response.setId(user.getId());
			response.setCreated(user.getCreated());
			response.setModified(user.getModified());
			response.setLast_login(user.getLast_login());
			response.setIsactive(user.getIsactive());			
			
			return  response;			
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
	}
	
	
}

	
