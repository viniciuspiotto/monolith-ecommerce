package edu.unifalmg.monolithecommerce.iam.application.DTO.commands;

import java.util.UUID;

public record UpdatePasswordCommand (
        String token,
        String oldPassword,
        String newPassword,
        String newConfirmPassword
){}
