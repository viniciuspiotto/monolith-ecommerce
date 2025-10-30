package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.embeddable;

import edu.unifalmg.monolithecommerce.iam.domain.model.enums.NationalIdType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class NationalIdEmbeddable {
    private String nationalId;
    @Enumerated(EnumType.STRING)
    private NationalIdType type;
}
