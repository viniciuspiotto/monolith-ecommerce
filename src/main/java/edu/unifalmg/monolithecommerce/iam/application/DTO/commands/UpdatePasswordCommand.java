package edu.unifalmg.monolithecommerce.iam.application.DTO.commands;

public record UpdatePasswordCommand (
        String email,
        String oldPassword,
        String newPassword,
        String newConfirmPassword
){}
