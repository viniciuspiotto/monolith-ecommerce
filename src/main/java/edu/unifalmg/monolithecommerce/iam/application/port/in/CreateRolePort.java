package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.RoleDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateRoleCommand;

public interface CreateRolePort {
    RoleDTO execute(CreateRoleCommand command);
}
