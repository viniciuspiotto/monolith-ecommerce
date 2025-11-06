package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.DeleteUserCommand;

public interface DeleteUserPort {
    Boolean execute(DeleteUserCommand cmd);
}
