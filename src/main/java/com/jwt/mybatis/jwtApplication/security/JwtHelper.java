package com.jwt.mybatis.jwtApplication.security;

import com.jwt.mybatis.jwtApplication.entity.UserEntity;
import com.jwt.mybatis.jwtApplication.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtHelper {

    public static final long JWT_TOKEN_VALIDITY = 300 * 60; // 5 Hours

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        UserEntity role = userRepository.findUserByUsername(userDetails.getUsername());
        claims.put("Role", role.getRole());
        claims.put("assc_id", role.getAsscId());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        byte[] secretBytes = secret.getBytes();
        SecretKey secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setIssuer("vaibhav")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

}
