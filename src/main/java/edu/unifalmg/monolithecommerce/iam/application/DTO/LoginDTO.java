package edu.unifalmg.monolithecommerce.iam.application.DTO;

public record LoginDTO (
        String token,
        String email,
        String password
) {
}
