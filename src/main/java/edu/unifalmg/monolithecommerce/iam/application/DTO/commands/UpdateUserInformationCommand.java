package edu.unifalmg.monolithecommerce.iam.application.DTO.commands;

public record UpdateUserInformationCommand(
        String email,
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
