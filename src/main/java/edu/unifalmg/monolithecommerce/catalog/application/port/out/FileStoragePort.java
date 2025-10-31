package edu.unifalmg.monolithecommerce.catalog.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.FileStorageDTO;

import java.util.Set;

public interface FileStoragePort {
    FileStorageDTO save(CreateModelCommand.FileCommand cmd, Set<String> allowedMimeTypes);
    void delete(String filename);
}
