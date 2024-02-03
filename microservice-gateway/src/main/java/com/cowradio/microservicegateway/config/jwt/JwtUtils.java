package com.cowradio.microservicegateway.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUtils {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    public boolean validateToken(String token) {
        return token != null && !token.isBlank() && token.startsWith("Bearer ");
    }

    public boolean isAdmin(String token){
        return extractRoles(token).stream().anyMatch(a -> a.equalsIgnoreCase("ADMIN"));
    }

    public boolean isUser(String token){
        return extractRoles(token).stream().anyMatch(a -> a.equalsIgnoreCase("USER"));
    }
    public List<String> extractRoles(String token){
        return extractAllClaims(token).get("roles", ArrayList.class);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(generateSignKey(SECRET_KEY)).build().parseClaimsJws(token).getBody();
    }
    public Key generateSignKey(String SECRET_KEY) {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
