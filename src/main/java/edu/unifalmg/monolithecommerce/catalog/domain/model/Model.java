package edu.unifalmg.monolithecommerce.catalog.domain.model;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import edu.unifalmg.monolithecommerce.catalog.domain.event.ModelRemovedEvent;
import edu.unifalmg.monolithecommerce.catalog.domain.event.ModelUpdatedEvent;
import edu.unifalmg.monolithecommerce.catalog.domain.model.enums.ModelStatus;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.*;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Model extends AbstractAggregateRoot<Model> {

    private final ModelId modelId;
    private String title;
    private String description;
    private Thumbnail thumbnail;
    private Money price;
    private UUID categoryId;
    private Rate averageRate;
    private ModelStatus status;
    private final List<Mesh> meshes = new ArrayList<>();
    private final List<Texture> textures = new ArrayList<>();

    public static Model create(String title, String description, Thumbnail thumbnail, Money price, UUID categoryId) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }

        if (thumbnail == null) {
            throw new IllegalArgumentException("Thumbnail cannot be null");
        }

        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if (categoryId == null){
            throw new IllegalArgumentException("Category id cannot be null");
        }

        return Model.builder()
                .modelId(new ModelId(UUID.randomUUID()))
                .title(title)
                .description(description)
                .thumbnail(thumbnail)
                .price(price)
                .categoryId(categoryId)
                .averageRate(Rate.zero())
                .status(ModelStatus.DRAFT)
                .build();
    }

    public void updateRate(Rate rate) {
        if(rate == null) {
            throw new IllegalArgumentException("Rate cannot be null");
        }
        this.averageRate = rate;
    }

    public void addMesh(Mesh mesh) {
        if(mesh == null){
            throw new IllegalArgumentException("Mesh cannot be null");
        }
        meshes.add(mesh);
    }

    public void removeMesh(Mesh mesh){
        if (meshes.size() == 1) {
            throw new IllegalArgumentException("Model must have at least 1 mesh");
        }
        if (meshes.isEmpty() || mesh == null){
            throw new IllegalArgumentException("Mesh cannot be empty or null");
        }
        meshes.remove(mesh);
    }

    public void addTexture(Texture texture) {
        if(texture == null){
            throw new IllegalArgumentException("Texture cannot be null");
        }
        textures.add(texture);
    }

    public void removeTexture(Texture texture){
        if (textures.size() == 1) {
            throw new IllegalArgumentException("Model must have at least 1 texture");
        }
        if (textures.isEmpty() || texture == null){
            throw new IllegalArgumentException("Texture cannot be empty or null");
        }
        textures.remove(texture);
    }

    public void renameModel(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        this.title = title;
    }

    public void changeDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        this.description = description;
    }

    public void changeThumbnail(Thumbnail thumbnail) {
        if (thumbnail == null) {
            throw new IllegalArgumentException("Thumbnail cannot be null");
        }
        this.thumbnail = thumbnail;
    }

    public void changePrice(Money price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        this.price = price;
    }

    public void changeCategory(UUID categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category id cannot be null");
        }
        this.categoryId = categoryId;
    }

    public static Model rehydrate(
            ModelId modelId,
            String title,
            String description,
            Thumbnail thumbnail,
            Money price,
            UUID categoryId,
            Rate averageRate,
            ModelStatus status,
            List<Mesh> meshes,
            List<Texture> textures
    ) {
        Model model = Model.builder()
                .modelId(modelId)
                .title(title)
                .description(description)
                .thumbnail(thumbnail)
                .price(price)
                .categoryId(categoryId)
                .averageRate(averageRate)
                .status(status)
                .build();

        if (meshes != null) {
            model.meshes.addAll(meshes);
        }
        if (textures != null) {
            model.textures.addAll(textures);
        }

        return model;
    }

    public void notifyModelUpdated() {
        this.registerEvent(new ModelUpdatedEvent(this.modelId.id()));
    }

    public void notifyModelRemoved() {
        this.registerEvent(new ModelRemovedEvent(this.modelId.id()));
    }

    public Collection<Object> getDomainEvents() {
        return this.domainEvents();
    }
}
