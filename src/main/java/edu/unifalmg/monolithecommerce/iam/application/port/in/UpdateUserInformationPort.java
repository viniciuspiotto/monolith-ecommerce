package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UpdateUserInformationDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateUserInformationCommand;

public interface UpdateUserInformationPort {
    UpdateUserInformationDTO execute(UpdateUserInformationCommand cmd);
}
