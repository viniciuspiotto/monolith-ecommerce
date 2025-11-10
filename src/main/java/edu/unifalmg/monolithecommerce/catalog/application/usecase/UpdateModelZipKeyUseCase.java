package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.UpdateZipKeyCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.ModelMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.UpdateModelZipKeyPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateModelZipKeyUseCase implements UpdateModelZipKeyPort {

    private final ModelRepositoryPort modelRepositoryPort;
    private final ModelMapper modelMapper;

    @Override
    public ModelDTO execute(UpdateZipKeyCommand cmd) {
        Model existingModel = modelRepositoryPort.findById(cmd.id())
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + cmd.id()));

        if (cmd.zipFileKey().isBlank()) {
            throw new IllegalArgumentException("zip key is required");
        }

        existingModel.changeZipKey(cmd.zipFileKey());

        Model savedModel = modelRepositoryPort.update(existingModel);

        return modelMapper.toDTO(savedModel);
    }
}
