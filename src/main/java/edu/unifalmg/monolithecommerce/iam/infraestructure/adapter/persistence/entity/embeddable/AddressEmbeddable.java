package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AddressEmbeddable {
    private String country;
    private String city;
    private String state;
    private String zip;
    private String street;
    private Integer number;
    private String neighborhood;
    private String complement;
}
