package edu.unifalmg.monolithecommerce.catalog.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;

import java.util.Optional;
import java.util.UUID;

public interface ModelRepositoryPort {
    Model create(Model model);
    Optional<Model> findById(UUID id);
    Model update(Model model);
    Model delete(Model model);
}
