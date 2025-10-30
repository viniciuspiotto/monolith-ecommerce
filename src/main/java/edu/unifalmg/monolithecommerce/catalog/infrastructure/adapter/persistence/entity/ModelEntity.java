package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.entity;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.ModelStatus;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.entity.embeddable.MeshEmbeddable;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.entity.embeddable.TextureEmbeddable;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.persistence.entity.embeddable.ThumbnailEmbeddable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "catalog_models")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelEntity {

    @Id
    private UUID id;

    private String title;
    private String description;

    @Column(precision = 19, scale = 4)
    private BigDecimal price;

    private double averageRate;

    @Enumerated(EnumType.STRING)
    private ModelStatus status;

    private UUID categoryId;

    @Embedded
    private ThumbnailEmbeddable thumbnail;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "catalog_model_meshes", joinColumns = @JoinColumn(name = "model_id"))
    private List<MeshEmbeddable> meshes;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "catalog_model_textures", joinColumns = @JoinColumn(name = "model_id"))
    private List<TextureEmbeddable> textures;
}
