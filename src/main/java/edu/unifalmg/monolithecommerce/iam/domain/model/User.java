package edu.unifalmg.monolithecommerce.iam.domain.model;

import edu.unifalmg.monolithecommerce.iam.domain.model.vo.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class User {

    private final UserId userId;
    private String name;
    private String lastName;
    private Email email;
    private HashedPassword hashedPassword;
    private Address address;
    private Role role;
    private NationalId nationalId;

    public static User create(String name, String lastName, Email email, HashedPassword hashedPassword, Role role, Address address, NationalId nationalId) {

        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if(lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException("Last cannot be null or empty");
        }

        if(role == null){
            throw new IllegalArgumentException("Role cannot be null");
        }

        if(address == null){
            throw new IllegalArgumentException("Address cannot be null.");
        }

        if(nationalId == null){
            throw new IllegalArgumentException("National Id cannot be null.");
        }

        return User.builder()
                .name(name)
                .lastName(lastName)
                .email(email)
                .hashedPassword(hashedPassword)
                .role(role)
                .address(address)
                .nationalId(nationalId)
                .build();
    }

    public void updateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        this.name = name;
    }

    public void updateLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
        this.lastName = lastName;
    }

    public void updateEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = email;
    }

    public void updateHashedPassword(HashedPassword hashedPassword) {
        if (hashedPassword == null) {
            throw new IllegalArgumentException("Hashed password cannot be null");
        }
        this.hashedPassword = hashedPassword;
    }

    public void updateAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        this.address = address;
    }

    public void updateRoleId(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = role;
    }

    public void updateNationalId(NationalId nationalId) {
        if (nationalId == null) {
            throw new IllegalArgumentException("NationalId cannot be null");
        }
        this.nationalId = nationalId;
    }

    public static User rehydrate(
            UserId userId,
            String name,
            String lastName,
            Email email,
            HashedPassword hashedPassword,
            Role role,
            Address address,
            NationalId nationalId
    ) {
        return User.builder()
                .userId(userId)
                .name(name)
                .lastName(lastName)
                .email(email)
                .hashedPassword(hashedPassword)
                .role(role)
                .address(address)
                .nationalId(nationalId)
                .build();
    }


}
