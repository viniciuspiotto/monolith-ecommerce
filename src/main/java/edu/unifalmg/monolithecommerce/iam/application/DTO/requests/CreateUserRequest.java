package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateUserCommand;

import java.util.UUID;

public record CreateUserRequest (
        String name,
        String lastName,
        String email,
        String password,
        UUID roleId,
        String NationalId,
        Address address
){
    public record Address(
            String country,
            String city,
            String state,
            String zip,
            String street,
            Integer number,
            String neighborhood,
            String complement) {}
}
