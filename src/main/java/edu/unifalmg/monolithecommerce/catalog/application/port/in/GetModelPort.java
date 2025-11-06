package edu.unifalmg.monolithecommerce.catalog.application.port.in;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.GetModelCommand;

public interface GetModelPort {
    ModelDTO execute(GetModelCommand cmd);
}
