package edu.unifalmg.monolithecommerce.catalog.application.port.out;

import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;

public interface ModelRepositoryPort {
    Model create(Model model);
}
