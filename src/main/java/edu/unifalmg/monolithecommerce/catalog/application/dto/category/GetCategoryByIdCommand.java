package edu.unifalmg.monolithecommerce.catalog.application.dto.category;

import java.util.UUID;

public record GetCategoryByIdCommand (
        UUID id
){
}
