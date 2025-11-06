package edu.unifalmg.monolithecommerce.iam.application.DTO.commands;

import java.util.UUID;

public record UpdateUserInformationCommand(
        String token,
        String name,
        String lastName,
        String nationalId,
        AddressCommand address
) {
    public record AddressCommand (
            String country,
            String city,
            String state,
            String zip,
            String street,
            Integer number,
            String neighborhood,
            String complement
    ){}
}
