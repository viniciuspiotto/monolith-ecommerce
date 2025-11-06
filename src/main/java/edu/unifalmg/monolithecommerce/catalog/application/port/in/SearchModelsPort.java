package edu.unifalmg.monolithecommerce.catalog.application.port.in;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.ModelSearchCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchModelsPort {
    Page<ModelSearchDTO> execute(ModelSearchCommand cmd, Pageable pageable);
}
