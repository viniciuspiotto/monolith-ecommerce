package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.entity.embeddable;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.MeshType;
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
public class MeshEmbeddable {
    private String uniqueName;
    private String url;
    private String filename;

    @Enumerated(EnumType.STRING)
    private MeshType type;
}
