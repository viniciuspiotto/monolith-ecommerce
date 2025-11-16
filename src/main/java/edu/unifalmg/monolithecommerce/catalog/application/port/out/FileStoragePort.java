package edu.unifalmg.monolithecommerce.catalog.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.FileStorageDTO;

import java.net.URL;
import java.util.Set;

public interface FileStoragePort {
    FileStorageDTO save(CreateModelCommand.FileCommand cmd, Set<String> allowedMimeTypes, boolean isPublic);
    void delete(String filename);
    URL generateUrl(String filename);
}
