package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.RoleDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.GetRoleByIdCommand;

public interface GetRoleByIdPort {
    RoleDTO execute(GetRoleByIdCommand command);
}
