package edu.unifalmg.monolithecommerce.catalog.application.dto.commands;

import java.util.UUID;

public record UpdateZipKeyCommand(UUID id, String zipFileKey) {}
