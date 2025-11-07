package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TokenGenerationService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.expiration-hours}")
    private long expirationHours;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            List<String> roles = List.of(user.getRole().getName());

            UUID uuid = user.getUserId().id();
            String customerIdString = uuid.toString();

            String token = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getEmail().getEmail())
                    .withClaim("roles", roles)
                    .withClaim("customerId", customerIdString)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            log.info("JWT token generated successfully for user: {}", user.getEmail());
            return token;
        } catch (JWTCreationException exception) {
            log.error("Error while generating JWT token", exception);
            throw new RuntimeException("Error while generating JWT token", exception);
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(expirationHours)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
