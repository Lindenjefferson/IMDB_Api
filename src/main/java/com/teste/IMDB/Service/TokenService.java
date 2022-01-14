package com.teste.IMDB.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
    
    @Value("${jwt.expiration}")
	private String expiration;

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(Long id) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + Long.parseLong(expiration));
		return Jwts.builder().setIssuer("localhost:8080")
                             .setSubject(id.toString())
                             .setIssuedAt(new Date())
				             .setExpiration(exp)
                             .signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public Long getTokenId(String token) {
		Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return Long.valueOf(body.getSubject());
	}

	public String getTokenFromHeader(String header) {
		if(header == null || header.isEmpty() || !header.startsWith("Bearer ")) return null;
		return header.substring(7, header.length());
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
