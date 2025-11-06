package edu.unifalmg.monolithecommerce.iam.application.DTO;


import java.util.UUID;

public record UserDTO (
    UUID userId,
    String name,
    String lastName,
    String email,
    RoleDTO role,
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

        public record RoleDTO(
                UUID roleId,
                String name,
                String description
        ){
        }

}
