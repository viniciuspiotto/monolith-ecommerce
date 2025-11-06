package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil  {

    @Value("${jwt.secret:defaultSecretKey}")
    private String SECRET_KEY;

    @Value("${jwt.expiration:3600000}")
    private long EXPIRATION_TIME;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }

    public String generateToken(UserEntity user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRoleId().getName())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(getAlgorithm());
    }

    public String extractRole(String token) {
        try {
            Algorithm algorithm = getAlgorithm();
            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return jwt.getClaim("role").asString();
        } catch (Exception e) {
            throw new JWTVerificationException("Invalid or expired token");
        }
    }


    public String extractUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (Exception e) {
            throw new JWTVerificationException("Invalid token");
        }
    }

    public Date extractExpiration(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt();
        } catch (Exception e) {
            throw new JWTVerificationException("Invalid token");
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public Boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            verifier.verify(token);
            return !isTokenExpired(token);
        } catch (JWTVerificationException e) {
            return false;
        }
    }

}
