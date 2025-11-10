package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.RemoveModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.ModelMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.RemoveModelPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.FileStoragePort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Mesh;
import edu.unifalmg.monolithecommerce.catalog.domain.model.vo.Texture;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RemoveModelUseCase implements RemoveModelPort {

    ModelRepositoryPort modelRepositoryPort;
    FileStoragePort fileStoragePort;
    ModelMapper modelMapper;

    public RemoveModelUseCase(
            ModelRepositoryPort modelRepositoryPort,
            @Qualifier("s3FileStorageAdapter") FileStoragePort fileStoragePort,
            ModelMapper modelMapper) {
        this.modelRepositoryPort = modelRepositoryPort;
        this.fileStoragePort = fileStoragePort;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ModelDTO execute(RemoveModelCommand cmd) {
        log.info("Initiating remove model with id: {}", cmd.id());

        Model modelToRemove = modelRepositoryPort.findById(cmd.id())
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + cmd.id()));

        modelToRemove.notifyModelRemoved();

        List<String> filesToDelete = new ArrayList<>();
        filesToDelete.add(modelToRemove.getThumbnail().getUniqueName());
        modelToRemove.getMeshes().stream()
                .map(Mesh::getUniqueName)
                .forEach(filesToDelete::add);
        modelToRemove.getTextures().stream()
                .map(Texture::getUniqueName)
                .forEach(filesToDelete::add);

        filesToDelete.forEach(fileStoragePort::delete);

        modelRepositoryPort.delete(modelToRemove);

        return modelMapper.toDTO(modelToRemove);
    }
}
