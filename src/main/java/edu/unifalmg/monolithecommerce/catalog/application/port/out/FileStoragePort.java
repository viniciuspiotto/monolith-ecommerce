package edu.unifalmg.monolithecommerce.catalog.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.application.dto.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.FileStorageResultDTO;

import java.util.Set;

public interface FileStoragePort {
    FileStorageResultDTO save(CreateModelCommand.FileCommand cmd, Set<String> allowedMimeTypes);
}
