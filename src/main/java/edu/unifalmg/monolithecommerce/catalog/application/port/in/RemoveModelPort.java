package edu.unifalmg.monolithecommerce.catalog.application.port.in;

import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelDTO;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.RemoveModelCommand;

public interface RemoveModelPort {
    ModelDTO execute(RemoveModelCommand cmd);
}
