package edu.unifalmg.monolithecommerce.iam.application.DTO.commands;

public record UpdateEmailCommand(
        String email,
        String passwordToConfirm,
        String oldEmail,
        String newEmail
){}
