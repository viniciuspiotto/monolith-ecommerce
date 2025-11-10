package edu.unifalmg.monolithecommerce.catalog.infrastructure.startup;

import edu.unifalmg.monolithecommerce.catalog.application.port.out.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Mesh;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Texture;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Thumbnail;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class CatalogInitializer implements CommandLineRunner {

    private final ModelRepositoryPort modelRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public void run(String... args) throws Exception {
        UUID categoryId1 = createCategoryIfNotFound(
                "Sci-fi",
                "Scenarios related to fiction"
        );

        UUID categoryId2 = createCategoryIfNotFound(
                "Fantasy",
                "Scenarios related to fantasy"
        );

        UUID modelId1 = createModelIfNotFound(
                "Low-poly Spaceship",
                "A low-poly model of a sci-fi spaceship, ready for games.",
                new Money(new BigDecimal("19.99")),
                categoryId1
        );

        UUID modelId2 = createModelIfNotFound(
                "Medieval Sword",
                "A high-quality PBR model of a medieval sword.",
                new Money(new BigDecimal("9.50")),
                categoryId2
        );

        log.info("Category created: {}", categoryId1);
        log.info("Category created: {}", categoryId2);
        log.info("Model created: {}", modelId1);
        log.info("Model created: {}", modelId2);
    }

    private UUID createModelIfNotFound(String title, String description, Money price, UUID categoryId) {
        Optional<Model> modelFounded = modelRepositoryPort.findByTitle(title);

        if (modelFounded.isPresent()) {
            log.warn("Model with title '{}' already exists. Skipping.", title);
            return modelFounded.get().getModelId().id();
        }

        String baseName = title.toLowerCase().replaceAll("\\s+", "_");

        Thumbnail thumbnail = Thumbnail.create(
                baseName + "_thumb.png",
                "",
                "thumbnail.png",
                "image/png"
        );

        Model newModel = Model.create(
                title,
                description,
                thumbnail,
                price,
                categoryId
        );

        Mesh mesh = Mesh.create(
                baseName + ".fbx",
                "model.fbx",
                "application/vnd.autodesk.fbx"
        );
        newModel.addMesh(mesh);

        Texture texture = Texture.create(
                baseName + "_tex.png",
                "texture.png",
                "image/png"
        );
        newModel.addTexture(texture);

        Model createdModel = modelRepositoryPort.create(newModel);
        return createdModel.getModelId().id();
    }

    private UUID createCategoryIfNotFound(String name, String description) {
        Optional<Category> categoryFounded = categoryRepositoryPort.findByName(name);

        if (categoryFounded.isPresent()) {
            log.warn("Category with name '{}' already exists. Skipping.", name);
            return categoryFounded.get().getCategoryId().id();
        }

        Category category = Category.create(name, description);
        categoryRepositoryPort.save(category);
        return category.getCategoryId().id();
    }
}
