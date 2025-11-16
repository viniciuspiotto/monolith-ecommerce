package edu.unifalmg.monolithecommerce.iam.application.DTO;

import java.util.UUID;

public record UpdateUserInformationDTO(
        UUID userId,
        String name,
        String lastName,
        String nationalId,
        UserDTO.AddressDTO address
){
    public record AddressDTO (
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
