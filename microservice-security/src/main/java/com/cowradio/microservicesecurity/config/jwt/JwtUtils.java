package com.cowradio.microservicesecurity.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {
    private final String SECRET_KEY = "6ed1eb0150e8f8fb285ec976ce500807406933985fbea5086c690eb221e53f5a";

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
        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(generateSignKey()).compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(generateSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Key generateSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
