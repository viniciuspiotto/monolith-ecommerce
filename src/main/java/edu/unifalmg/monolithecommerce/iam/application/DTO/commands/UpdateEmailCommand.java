package edu.unifalmg.monolithecommerce.iam.application.DTO.commands;

import java.util.UUID;

public record UpdateEmailCommand(
        String token,
        String oldEmail,
        String newEmail
){}
