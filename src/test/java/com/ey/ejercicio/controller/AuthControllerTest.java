package com.ey.ejercicio.controller;

import com.ey.ejercicio.dto.*;
import com.ey.ejercicio.model.Phone;
import com.ey.ejercicio.model.Roles;
import com.ey.ejercicio.model.User;
import com.ey.ejercicio.repository.RolesRepository;
import com.ey.ejercicio.repository.UserRepository;
import com.ey.ejercicio.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolesRepository roleRepositorio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Authentication authentication;

    List<PhonesDto> lstPhones;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();

        lstPhones = new ArrayList<>();
        PhonesDto phone = new PhonesDto();
        phone.setNumber("1234");
        phone.setCitycode("12");
        phone.setCountrycode("13");
        lstPhones.add(phone);
    }

    @Test
    void authenticateUser_ShouldReturnToken_WhenCredentialsAreValid() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsernameOrEmail("test@example.com");
        loginDTO.setPassword("password");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("fake-jwt-token");

        ResponseEntity<JWTAuthResonseDTO> response = authController.authenticateUser(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("fake-jwt-token", response.getBody().getToken());
    }

    @Test
    void registerUser_ShouldReturnBadRequest_WhenEmailAlreadyExists() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setPhones(lstPhones);

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        ResponseEntity<Object> response = authController.registerUser(userDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El correo ya ristrado", ((MensajeDto) response.getBody()).getMensaje());
    }

    @Test
    void registerUser_ShouldRegisterUser_WhenEmailIsUnique() {
        UserDto userDto = new UserDto();
        userDto.setName("Usertest");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setPhones(lstPhones);

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        when(modelMapper.map(userDto.getPhones(),  new TypeToken<List<Phone>>(){}.getType())).thenReturn(new ArrayList<>());
        when(modelMapper.map(user, ResponseDto.class)).thenReturn(new ResponseDto());

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encoded-password");
        when(roleRepositorio.findByName(any())).thenReturn(Optional.of(new Roles()));
        when(jwtTokenProvider.generateToken(any())).thenReturn("fake-jwt-token");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));

        ResponseEntity<Object> response = authController.registerUser(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}