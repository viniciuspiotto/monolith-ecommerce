package edu.unifalmg.monolithecommerce.iam.domain.model.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

    private String country;
    private String city;
    private String state;
    private String zip;
    private String street;
    private Integer number;
    private String neighborhood;
    private String complement;

    public static Address create (String country, String city, String state, String zip, String street, Integer number, String neighborhood, String complement) {
        if(country == null || country.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if(city == null || city.isEmpty()){
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if(state == null || state.isEmpty()){
            throw new IllegalArgumentException("State cannot be null or empty");
        }
        if(zip == null || zip.isEmpty()){
            throw new IllegalArgumentException("Zip cannot be null or empty");
        }
        if(street == null || street.isEmpty()){
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if(number == null){
            throw new IllegalArgumentException("Number cannot be null or empty");
        }
        return new Address(country, city, state, zip, street, number, neighborhood, complement);
    }

}
