package com.ey.ejercicio.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ey.ejercicio.dto.ResponseDto;
import com.ey.ejercicio.dto.UserDto;
import com.ey.ejercicio.model.User;

@Service
public class UserServices{
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUsuario()  throws Exception {
		try {
			User user = new User();
			user.setName("Prueba");
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
			
			User user = userRepository.findById(entity.getIdUser()).get();
			user.setIdUser(entity.getIdUser());
			user.setEmail(entity.getEmail());
			user.setLast_update(fechaActual);
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
		userModel.setName(user.getName());
		userModel.setEmail(user.getEmail());
		userModel.setPassword(user.getPassword());
		userModel.setIs_active(true);
		userModel.setPhones(null);		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();  
		String fechaActual = formatter.format(date);
		userModel.setCreated(fechaActual);
		
		userModel = userRepository.save(userModel);		
		userModel = userRepository.findById(userModel.getIdUser()).get();
		
		response.setId(userModel.getIdUser());
		response.setCreated(userModel.getCreated());
		response.setModified(userModel.getLast_update());		
		
		String last_login = userModel.getLast_login();
		if(last_login == null || last_login.trim().isEmpty()) {
			response.setLast_login(userModel.getCreated());	
		}
		else {
			response.setLast_login(userModel.getLast_login());	
		}
		
		response.setIsactive(userModel.getIs_active());		
		
		return response;
		//return userRepository.getByPassword(pass);
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}		
	}
	
	public Boolean getExistMail(String mail) throws Exception {
		try {		
			Integer total = userRepository.getExistEmail(mail);
			return total > 0 ? true : false;
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
	}
	
	public Long getUserByEmailPassName(String mail, String password, String name) throws Exception {
		try {
			Long id_user = userRepository.getIdUserByEmailPassName(mail, password, name);
			return  id_user == null ? (long) 0 : id_user;			
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
	}
	
	public ResponseDto getUserById(Long id_user) throws Exception {
		try {
			ResponseDto response = new ResponseDto();
			User user = userRepository.findById(id_user).get();
			response.setId(user.getIdUser());
			response.setCreated(user.getCreated());
			response.setModified(user.getLast_update());
			response.setLast_login(user.getLast_login());
			response.setIsactive(user.getIs_active());			
			
			return  response;			
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
	}
	
	
}

	
