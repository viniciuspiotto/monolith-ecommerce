package edu.unifalmg.monolithecommerce.catalog.application.dto.commands;

import java.util.UUID;

public record GetCategoryByIdCommand (
        UUID id
){
}
