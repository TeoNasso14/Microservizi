package com.example.todo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key signingKey;
    private static final long VALIDITY_MS = 24 * 60 * 60 * 1000; // 1 giorno

    public JwtUtil() {
      // Genera la Key a partire dai byte della secret
      this.signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(UserDetails user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_MS))
            .signWith(signingKey, SignatureAlgorithm.HS256)  // usa la Key
            .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)                      // imposta la Key
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token, UserDetails user) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.getSubject().equals(user.getUsername())
                && !claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            // token non valido o scaduto
            return false;
        }
    }
}
