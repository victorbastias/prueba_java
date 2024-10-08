package com.ey.ejercicio.controller;

import com.ey.ejercicio.dto.*;
import com.ey.ejercicio.model.Phone;
import com.ey.ejercicio.model.Roles;
import com.ey.ejercicio.model.User;
import com.ey.ejercicio.repository.RolesRepository;
import com.ey.ejercicio.repository.UserRepository;
import com.ey.ejercicio.security.JwtTokenProvider;
import com.ey.util.RolEnum;
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

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

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
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        MensajeDto mensaje = new MensajeDto();
        try {
            final var user = userRepository.findByEmail(loginDTO.getEmail());
            if (user.isPresent() && passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String fechaActual = formatter.format(date);
                user.get().setLast_login(fechaActual);
                userRepository.save(user.get());
                mensaje.setMensaje("login correcto");
                return ResponseEntity.ok(mensaje);
            }

            mensaje.setMensaje("Credenciales inválidas");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mensaje);
        } catch (Exception ex) {
            mensaje.setMensaje("Error inesperado: " + ex.getMessage());
            return new ResponseEntity<>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/v1/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDto userDto) {
        MensajeDto mensaje = new MensajeDto();
        try {
            ResponseDto response;
            if (userRepository.existsByEmail(userDto.getEmail())) {
                mensaje.setMensaje("El correo ya ristrado");
                return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String fechaActual = formatter.format(date);

            User user = modelMapper.map(userDto, User.class);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setIsactive(true);
            user.setCreated(fechaActual);
            user.setPhones(new ArrayList<>());

            Optional<Roles> roles = roleRepositorio.findByName(RolEnum.NORMAL.name());
            roles.ifPresent(value -> user.setRoles(Collections.singleton(value)));

            List<Phone> lstPhones = modelMapper.map(userDto.getPhones(), new TypeToken<List<Phone>>() {
            }.getType());
            lstPhones.forEach(user::addToPhoneList);

            userRepository.save(user);
            user.setId(UUID.randomUUID().getMostSignificantBits());
            response = modelMapper.map(user, ResponseDto.class);

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.setToken(jwtTokenProvider.generateToken(authentication));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            mensaje.setMensaje("Error inesperado: " + ex.getMessage());
            return new ResponseEntity<>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}