package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelSearchDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.ModelSearchCommand;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.SearchModelsPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.SearchModelsGateway;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SearchModelsUseCase implements SearchModelsPort {

    private final SearchModelsGateway searchModelsGateway;

    private final Timer searchFlowTimer;

    public SearchModelsUseCase(SearchModelsGateway searchModelsGateway,
                               MeterRegistry meterRegistry) {

        this.searchModelsGateway = searchModelsGateway;

        this.searchFlowTimer = Timer.builder("ecommerce.catalog.search.flow")
                .description("Measures the duration of the search models flow")
                .publishPercentileHistogram(true)
                .register(meterRegistry);
    }

    @Override
    @WithSpan("usecase.searchModels")
    public Page<ModelSearchDTO> execute(ModelSearchCommand cmd, Pageable pageable) {

        log.info("Initiating model search with query: '{}'",
                cmd.toString());

        return searchFlowTimer.record(() -> {
            Page<ModelSearchDTO> results = searchModelsGateway.search(cmd, pageable);

            log.info("Found {} models on page {}", results.getNumberOfElements(), pageable.getPageNumber());

            return results;
        });
    }
}
