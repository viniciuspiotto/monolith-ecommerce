package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.ModelSearchCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelSearchDTO;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.SearchModelsPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.SearchModelsGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SearchModelsUseCase implements SearchModelsPort {

    private final SearchModelsGateway searchModelsGateway;

    @Override
    public Page<ModelSearchDTO> execute(ModelSearchCommand cmd, Pageable pageable) {

        log.info("Initiating model search with query: '{}' and category: '{}'",
                cmd.textQuery(), cmd.categoryId());

        Page<ModelSearchDTO> results = searchModelsGateway.search(cmd, pageable);

        log.info("Found {} models on page {}", results.getNumberOfElements(), pageable.getPageNumber());

        return results;
    }
}
