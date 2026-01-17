package com.back.gaon.domain.auth.jwt;

import com.back.gaon.domain.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-ttl}")
    private long accessTokenTtlSeconds;

    @Value("${jwt.refresh-token-ttl}")
    private long refreshTokenTtlSeconds;

    private byte[] keyBytes;


    @PostConstruct
    public void init() {
        this.keyBytes = secretKey.getBytes();
    }

    public String createAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getLoginId()))
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenTtlSeconds * 1000))
                .signWith(SignatureAlgorithm.HS256, keyBytes)
                .compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getLoginId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenTtlSeconds * 1000))
                .signWith(SignatureAlgorithm.HS256, keyBytes)
                .compact();
    }

    public Long validateAndExtractUserId(String token) {
        return Long.parseLong(
                Jwts.parser()
                        .setSigningKey(keyBytes)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }

    public String extractRole(String token) {
        return Jwts.parser()
                .setSigningKey(keyBytes)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public long getAccessTokenTtlSeconds() {
        return accessTokenTtlSeconds;
    }

    public long getRefreshTokenTtlSeconds() {
        return refreshTokenTtlSeconds;
    }
}