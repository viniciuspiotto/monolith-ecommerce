package edu.unifalmg.monolithecommerce.iam.application.DTO.requests;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateUserInformationCommand;
import edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation.AtLeastOneFieldProvided;

@AtLeastOneFieldProvided
public record UpdateUserInformationRequest(
        String name,
        String lastName,
        String nationalId,
        Address address
) {
    public record Address(
            String country,
            String city,
            String state,
            String zip,
            String street,
            Integer number,
            String neighborhood,
            String complement
    ) {
    }
}