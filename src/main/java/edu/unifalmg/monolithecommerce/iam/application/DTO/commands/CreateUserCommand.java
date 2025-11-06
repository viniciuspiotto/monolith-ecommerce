package edu.unifalmg.monolithecommerce.iam.application.DTO.commands;

import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Address;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.NationalId;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.UserId;

import java.util.UUID;

public record CreateUserCommand (
        String name,
        String lastName,
        String email,
        String password,
        String confirmPassword,
        String nationalId,
        AddressCommand address
){
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