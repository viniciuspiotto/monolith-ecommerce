package edu.unifalmg.monolithecommerce.shared.infraestructure.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TokenValidationService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            log.warn("JWT token validation failed: {}", exception.getMessage());
            return null;
        }
    }

    public String getSubject(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public List<String> getRoles(DecodedJWT decodedJWT) {
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        return roles != null ? roles : Collections.emptyList();
    }
}
