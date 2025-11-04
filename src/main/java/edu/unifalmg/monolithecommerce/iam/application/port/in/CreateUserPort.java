package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateUserCommand;

public interface CreateUserPort {
    UserDTO execute(CreateUserCommand cmd);
}
