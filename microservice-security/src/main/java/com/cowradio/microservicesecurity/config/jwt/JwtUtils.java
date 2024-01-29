package com.cowradio.microservicesecurity.config.jwt;

import com.cowradio.microservicesecurity.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtils {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    public boolean validateUser(UserDetails userDetails, String token) {
        return (extractUsername(token).equals(userDetails.getUsername())) && isNotExpired(token);
    }

    private boolean isNotExpired(String token) {
            Date expirationDate = extractClaim(token, Claims::getExpiration);
            return expirationDate != null && !expirationDate.before(new Date(System.currentTimeMillis()));
    }

    public String createToken(UserDetails userDetails){
        HashMap<String, Object> extraClaims = new HashMap<>();
        return createToken(extraClaims, userDetails);
    }

    public String createToken(Map<String, Object> extraClaims, UserDetails userDetails){

        List<String> rolesAsStrings = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(userDetails.getUsername())
                .claim("roles", rolesAsStrings)
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(generateSignKey(SECRET_KEY)).compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(generateSignKey(SECRET_KEY)).build().parseClaimsJws(token).getBody();
    }

    private Key generateSignKey(String SECRET_KEY){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
