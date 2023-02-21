package com.service;

import com.properties.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTTokenService {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; //24 h

    private final JWTProperties jwtProperties;

    public String findUsernameFromToken(String token) {
        return exportToken(token, Claims::getSubject);
    }

    public Date findExpirationFromToken(String token) {
        return exportToken(token, Claims::getExpiration);
    }

    private <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claimsTFunction.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
            .signWith(getSecretKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getSecretKey() {
        byte[] key = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(key);
    }
}
