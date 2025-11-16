package edu.unifalmg.monolithecommerce.catalog.application.port.in;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.CreateModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;

public interface CreateModelPort {
    ModelDTO execute(CreateModelCommand cmd);
}
