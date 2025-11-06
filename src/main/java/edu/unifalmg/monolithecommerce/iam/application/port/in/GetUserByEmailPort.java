package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.GetUserByEmailCommand;

public interface GetUserByEmailPort {
    UserDTO execute(GetUserByEmailCommand cmd);
}
