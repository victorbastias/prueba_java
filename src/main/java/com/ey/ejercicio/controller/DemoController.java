package com.ey.ejercicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ey.ejercicio.dto.ResponseDto;
import com.ey.ejercicio.dto.UserDto;
import com.ey.ejercicio.model.Error;
import com.ey.ejercicio.model.User;
import com.ey.ejercicio.services.UserServices;

@RestController
public class DemoController {
	
	@Autowired
	private UserServices service;
		
		
	@RequestMapping(value = "/v1/user/", method = RequestMethod.GET)
	public ResponseEntity<Object> GetUsuario() {
		try {
			User response = new User();
			response = service.getUsuario();
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		
		}catch(Exception ex) {
			Error error = new Error();
			error.setMensaje(ex.getMessage());
			return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		}
	}	
	
	
	@RequestMapping(value = "/v1/usersave/", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> SetUsuario(@RequestBody UserDto user) throws Exception{
		ResponseDto response = new ResponseDto();
		
		try {
			String mail = user.getEmail();
			user.setPassword(com.ey.util.Util.cryptWithMD5(user.getPassword()));
			if(mail == null || mail.length() == 0) {
				Error error = new Error();
				error.setMensaje("Debe completar el campo de correo");
				return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
				
			}else if(!com.ey.util.Util.isEmailValid(mail)) {
				Error error = new Error();
				error.setMensaje("El formato del correo incorrecto (aaaaaa@dominio.cl)");
				return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
			}
			//else if(!com.ey.util.Util.isPasswordValid(user.getPassword())) {
			//	Error error = new Error();
			//	error.setMensaje("El password debe contener 1 mayúscula, minusculas y 2 numeros");
			//	return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
			//}
			else {
				//1.- Revisar si existe el usuario con nombre, correo y password, 
				//	  si existe se actuaaliza la fecha de login y se actualizan los datos
				//2.- generar token
				Long idUsuario = service.getUserByEmailPassName(mail, user.getPassword(), user.getName());
				if(idUsuario > 0) {
					//1.-actualizar datos del usuario
					user.setIdUser(idUsuario);
					service.setUpdateUsuario(user);
					
					//2.- buiscar datos del usuario ok
					response = service.getUserById(idUsuario);
					
					//3.- generar token
					response.setToken("token de prueba");					
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				
				}else if(service.getExistMail(mail)) {//hace un count de los mail, si es mayor a 0 significa que encontró el mail				
					Error error = new Error();
					error.setMensaje("El correo ya registrado");
					return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
					
				}else {//Usuario no existe, se guarda y se retorna usuario + token
					response = service.setUsuario(user);					
					response.setToken("token de prueba");
				}				
			}		
		}catch (Exception ex) {
			Error error = new Error();
			error.setMensaje(ex.getMessage());
			return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		}
	    return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
