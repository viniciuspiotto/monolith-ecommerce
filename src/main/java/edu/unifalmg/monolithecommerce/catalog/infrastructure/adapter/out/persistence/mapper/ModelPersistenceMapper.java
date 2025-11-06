package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.mapper;

import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.*;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.ModelEntity;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.embeddable.MeshEmbeddable;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.embeddable.TextureEmbeddable;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.embeddable.ThumbnailEmbeddable;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ModelPersistenceMapper {
    @Mapping(source = "modelId.id", target = "id")
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "averageRate.value", target = "averageRate")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "textures", target = "textures")
    @Mapping(source = "meshes", target = "meshes")
    ModelEntity toEntity(Model model);

    @AfterMapping
    default void transferDomainEvents(@MappingTarget ModelEntity entity, Model model) {
        entity.setDomainEvents(model.getDomainEvents());
    }

    ThumbnailEmbeddable thumbnailToEmbeddable(Thumbnail thumbnail);
    MeshEmbeddable meshToEmbeddable(Mesh mesh);
    TextureEmbeddable textureToEmbeddable(Texture texture);

    default Model toDomain(ModelEntity entity) {
        if (entity == null) {
            return null;
        }

        ModelId modelId = map(entity.getId());
        Money price = map(entity.getPrice());
        Rate averageRate = map(entity.getAverageRate());
        Thumbnail thumbnail = embeddableToThumbnail(entity.getThumbnail());

        List<Mesh> meshes = (entity.getMeshes() == null) ? new ArrayList<>() :
                entity.getMeshes().stream()
                        .map(this::embeddableToMesh)
                        .toList();

        List<Texture> textures = (entity.getTextures() == null) ? new ArrayList<>() :
                entity.getTextures().stream()
                        .map(this::embeddableToTexture)
                        .toList();

        return Model.rehydrate(
                modelId,
                entity.getTitle(),
                entity.getDescription(),
                thumbnail,
                price,
                entity.getCategoryId(),
                averageRate,
                entity.getStatus(),
                meshes,
                textures
        );
    }

    default Thumbnail embeddableToThumbnail(ThumbnailEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }
        return Thumbnail.create(
                embeddable.getUniqueName(),
                embeddable.getUrl(),
                embeddable.getFilename(),
                embeddable.getType().getMimeType()
        );
    }

    default Mesh embeddableToMesh(MeshEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }

        return Mesh.create(
                embeddable.getUniqueName(),
                embeddable.getUrl(),
                embeddable.getFilename(),
                embeddable.getType().getMimeType()
        );
    }

    default Texture embeddableToTexture(TextureEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }

        return Texture.create(
                embeddable.getUniqueName(),
                embeddable.getUrl(),
                embeddable.getFilename(),
                embeddable.getType().getMimeType()
        );
    }


    default ModelId map(UUID id) {
        return new ModelId(id);
    }

    default Money map(BigDecimal decimal) {
        return new Money(decimal);
    }

    default Rate map(double value) {
        return new Rate(value);
    }
}
