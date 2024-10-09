package com.ey.ejercicio.security;

import com.ey.ejercicio.model.Roles;
import com.ey.ejercicio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 final var user = userRepository.findByEmail(email);
		 if(user.isPresent()){
			 return new User(user.get().getEmail(), user.get().getPassword(), mapRoles(user.get().getRoles()));
		 }
		 throw  new UsernameNotFoundException("Usuario no encontrado");
	}

	private Collection<? extends GrantedAuthority> mapRoles(Set<Roles> roles){
		return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
	}
}
