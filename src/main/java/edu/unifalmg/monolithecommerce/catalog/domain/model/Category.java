package edu.unifalmg.monolithecommerce.catalog.domain.model;

import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.CategoryId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Category {
    private final CategoryId categoryId;
    private String name;
    private String description;

    public static Category create(String name, String description){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if(description == null || description.isEmpty()){
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        return Category.builder()
                .categoryId(new CategoryId(UUID.randomUUID()))
                .name(name)
                .description(description)
                .build();
    }

    public void changeName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void changeDescription(String description){
        if(description == null || description.isEmpty()){
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = name;
    }

    public static Category rehydrate(
            CategoryId categoryId,
            String name,
            String description
    ) {
        return Category.builder()
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .build();
    }
}