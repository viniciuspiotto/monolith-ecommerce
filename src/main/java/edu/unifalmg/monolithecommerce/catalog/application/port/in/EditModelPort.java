package edu.unifalmg.monolithecommerce.catalog.application.port.in;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.EditModelCommand;

public interface EditModelPort {
    ModelDTO execute(EditModelCommand cmd);
}
