package edu.unifalmg.monolithecommerce.catalog.infrastructure.startup;

import com.github.javafaker.Faker;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.CategoryRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.event.ModelUpdatedEvent;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Category;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Mesh;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Texture;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Thumbnail;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class CatalogInitializer implements CommandLineRunner {

    private final ModelRepositoryPort modelRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Faker faker = new Faker(Locale.ENGLISH);

    private static final int NUMBER_OF_CATEGORIES_TO_CREATE = 5;
    private static final int NUMBER_OF_MODELS_TO_CREATE = 1000;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (modelRepositoryPort.count() > 0) {
            log.info("Database already seeded. Skipping fake data generation.");
            return;
        }

        log.info("Starting database seeding with {} categories and {} models...",
                NUMBER_OF_CATEGORIES_TO_CREATE, NUMBER_OF_MODELS_TO_CREATE);

        List<UUID> categoryIds = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CATEGORIES_TO_CREATE; i++) {
            String department = faker.commerce().department();
            UUID categoryId = createCategoryIfNotFound(
                    department,
                    "Models and assets related to " + department
            );
            categoryIds.add(categoryId);
        }
        log.info("{} categories created.", categoryIds.size());


        int modelsCreatedCount = 0;
        for (int i = 0; i < NUMBER_OF_MODELS_TO_CREATE; i++) {
            String productName = faker.commerce().productName();
            UUID randomCategoryId = categoryIds.get(faker.number().numberBetween(0, categoryIds.size()));

            UUID modelId = createModelIfNotFound(
                    productName,
                    faker.lorem().paragraph(2),
                    new Money(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 500))),
                    randomCategoryId
            );
            if (modelId != null) {
                modelsCreatedCount++;
            }
        }

        log.info("Database seeding finished. {} models created.", modelsCreatedCount);
    }

    private UUID createModelIfNotFound(String title, String description, Money price, UUID categoryId) {
        Optional<Model> modelFounded = modelRepositoryPort.findByTitle(title);

        if (modelFounded.isPresent()) {
            log.debug("Model with title '{}' already exists (Faker collision). Skipping.", title);
            return null;
        }

        String baseName = title.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "_");

        if (baseName.length() > 50) {
            baseName = baseName.substring(0, 50);
        }
        if (baseName.isEmpty()) {
            baseName = "model_" + UUID.randomUUID().toString().substring(0, 8);
        }


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

        UUID id = createdModel.getModelId().id();

        log.debug("publishing modelUpdatedEvent for modelId: {}", id);
        applicationEventPublisher.publishEvent(new ModelUpdatedEvent(id));

        return createdModel.getModelId().id();
    }

    private UUID createCategoryIfNotFound(String name, String description) {
        Optional<Category> categoryFounded = categoryRepositoryPort.findByName(name);

        if (categoryFounded.isPresent()) {
            log.debug("Category with name '{}' already exists (Faker collision). Skipping.", name);
            return categoryFounded.get().getCategoryId().id();
        }

        Category category = Category.create(name, description);
        categoryRepositoryPort.save(category);
        return category.getCategoryId().id();
    }
}