package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.GetModelNameByIdPort;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.api.ModelId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetModelNameByIdUseCase implements GetModelNameByIdPort {

    private final ModelRepositoryPort modelRepository;

    @Override
    public String execute(ModelId modelId){
        Optional<Model> model = modelRepository.findById(modelId.id());
        if(model.isEmpty()){
            throw new IllegalArgumentException("Model not found");
        }
        return model.get().getTitle();
    }
}
