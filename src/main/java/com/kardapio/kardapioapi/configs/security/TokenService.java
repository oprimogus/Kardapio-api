package com.kardapio.kardapioapi.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class TokenService {

    @Value("${api.configs.security.token.secret}")
    private String secret;

    public String createToken(UserModel user) {
        try {
            Algorithm algorithm =  Algorithm.HMAC256(secret);
            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            String[] rolesArray = roles.toArray(new String[0]);
            return JWT.create()
                    .withIssuer("Kardapio-api")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId().toString())
                    .withArrayClaim("roles", rolesArray)
                    .withExpiresAt(createExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token:", exception);
        }
    }

    public String validateToken (String token) {
        try {
            Algorithm algorithm =  Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Kardapio-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    public Boolean isValidToken (String token) {
        try {
            Algorithm algorithm =  Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private Instant createExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));

    }
}
