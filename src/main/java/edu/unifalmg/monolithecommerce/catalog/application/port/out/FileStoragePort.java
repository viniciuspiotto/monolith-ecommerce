package edu.unifalmg.monolithecommerce.catalog.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.response.FileStorageResponse;

import java.util.Set;

public interface FileStoragePort {
    FileStorageResponse save(CreateModelCommand.FileCommand cmd, Set<String> allowedMimeTypes);
}
