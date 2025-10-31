package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.UUID;


@Document(indexName = "catalog_models")
public record ModelSearchDocument(

        @Id
        String id,

        @Field(type = FieldType.Text, name = "title")
        String title,

        @Field(type = FieldType.Text, name = "description")
        String description,

        @Field(type = FieldType.Double, name = "price")
        BigDecimal priceAmount,

        @Field(type = FieldType.Keyword, name = "category_id")
        UUID categoryId,

        @Field(type = FieldType.Double, name = "average_rate")
        Double averageRate,

        @Field(type = FieldType.Text, name = "thumbnail_url")
        String thumbnailUrl
) {
}
