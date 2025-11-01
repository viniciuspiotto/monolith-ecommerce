package edu.unifalmg.monolithecommerce.catalog.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.ModelSearchCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchModelsGateway {
    Page<ModelSearchDTO> search(ModelSearchCommand cmd, Pageable pageable);
}
