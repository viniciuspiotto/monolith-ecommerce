package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateEmailCommand;

public interface UpdateEmailPort {
    Boolean execute(UpdateEmailCommand cmd);
}
