package edu.unifalmg.monolithecommerce.catalog.application.dto;

import java.util.UUID;

public record GetCategoryByIdCommand (
        UUID id
){
}
