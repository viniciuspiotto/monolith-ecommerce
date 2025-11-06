package edu.unifalmg.monolithecommerce.iam.application.DTO.commands;

public record LoginCommand (
        String email,
        String password
) {
}
