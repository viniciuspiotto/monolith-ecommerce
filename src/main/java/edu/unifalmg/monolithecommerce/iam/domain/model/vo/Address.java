package edu.unifalmg.monolithecommerce.iam.domain.model.vo;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateUserCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

    private final String country;
    private final String city;
    private final String state;
    private final String zip;
    private final String street;
    private final Integer number;
    private final String neighborhood;
    private final String complement;

    public static Address create(String country, String city, String state, String zip, String street, Integer number, String neighborhood, String complement) {
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }
        if (zip == null || zip.isEmpty()) {
            throw new IllegalArgumentException("Zip cannot be null or empty");
        }
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (number == null) {
            throw new IllegalArgumentException("Number cannot be null");
        }

        return new Address(country, city, state, zip, street, number, neighborhood, complement);
    }

    public static Address rehydrate(String country, String city, String state, String zip, String street, Integer number, String neighborhood, String complement) {
        return new Address(country, city, state, zip, street, number, neighborhood, complement);
    }
}
