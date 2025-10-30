package edu.unifalmg.monolithecommerce.iam.application.DTO;


import java.util.UUID;

public record UserDTO (
    String name,
    String lastName,
    String email,
    String password,
    UUID roleId,
    String nationalId,
    AddressDTO address
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
