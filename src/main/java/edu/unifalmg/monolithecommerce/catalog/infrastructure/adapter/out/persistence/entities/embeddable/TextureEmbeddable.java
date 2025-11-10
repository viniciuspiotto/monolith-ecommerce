package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.embeddable;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.TextureType;
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
public class TextureEmbeddable {
    private String uniqueName;
    private String filename;

    @Enumerated(EnumType.STRING)
    private TextureType type;
}
