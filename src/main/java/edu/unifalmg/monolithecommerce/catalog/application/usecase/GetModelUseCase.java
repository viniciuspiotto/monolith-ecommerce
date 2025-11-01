package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.GetModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.mapper.ModelMapper;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.GetModelPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetModelUseCase implements GetModelPort {

    private final ModelRepositoryPort  modelRepositoryPort;
    private final ModelMapper modelMapper;

    @Override
    public ModelDTO execute(GetModelCommand cmd) {
        Model model = modelRepositoryPort.findById(cmd.id())
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + cmd.id()));

        return modelMapper.toDTO(model);
    }
}
