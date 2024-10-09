package com.ey.ejercicio.security;

import com.ey.ejercicio.exceptions.EjercicioAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecretBase64;

	private SecretKey jwtSecret;
	
	@Value("${app.jdt-expiration-milliseconds}")
	private int jwtExpirationInMs;

	public JwtTokenProvider() {
		// La clave secreta se inicializa en el método init()
	}

	@PostConstruct
	public void init() {
		byte[] decodedKey = Base64.getDecoder().decode(Base64.getEncoder().encodeToString(jwtSecretBase64.getBytes()));
		this.jwtSecret = Keys.hmacShaKeyFor(decodedKey);
	}
	
	public String generateToken(Authentication authentication) {

		String username = authentication.getName();
		Date actualDate = new Date();
		Date expirationDate = new Date(actualDate.getTime() + jwtExpirationInMs);

		return Jwts.builder()
				.subject(username)
				.issuedAt(actualDate)
				.expiration(expirationDate)
				.signWith(jwtSecret)
				.compact();
	}

	
	public String getUsernameJWT(String token) {

		Claims claims = Jwts.parser()
				.verifyWith(jwtSecret)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
					.verifyWith(jwtSecret)
					.build()
					.parseSignedClaims(token);
			return true;
		}catch (MalformedJwtException ex) {
			throw new EjercicioAppException(HttpStatus.BAD_REQUEST,"Token JWT no valida");
		}
		catch (ExpiredJwtException ex) {
			throw new EjercicioAppException(HttpStatus.BAD_REQUEST,"Token JWT caducado");
		}
		catch (UnsupportedJwtException ex) {
			throw new EjercicioAppException(HttpStatus.BAD_REQUEST,"Token JWT no compatible");
		}
		catch (IllegalArgumentException ex) {
			throw new EjercicioAppException(HttpStatus.BAD_REQUEST,"La cadena claims JWT esta vacia");
		}
	}
}
