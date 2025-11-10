package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities;

import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.ModelStatus;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.embeddable.MeshEmbeddable;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.embeddable.TextureEmbeddable;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.embeddable.ThumbnailEmbeddable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.math.BigDecimal;
import java.util.*;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "catalog_model_meshes", joinColumns = @JoinColumn(name = "model_id"))
    private List<MeshEmbeddable> meshes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "catalog_model_textures", joinColumns = @JoinColumn(name = "model_id"))
    private List<TextureEmbeddable> textures;

    @Transient
    private final transient List<Object> domainEvents = new ArrayList<>();

    @DomainEvents
    public Collection<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @AfterDomainEventPublication
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    public void setDomainEvents(Collection<Object> events) {
        this.domainEvents.addAll(events);
    }
}
