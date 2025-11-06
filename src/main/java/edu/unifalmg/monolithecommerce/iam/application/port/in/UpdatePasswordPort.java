package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdatePasswordCommand;

public interface UpdatePasswordPort {
    Boolean execute(UpdatePasswordCommand cmd);
}
