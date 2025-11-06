package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.LoginCommand;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;

public interface LoginPort {
    User execute(LoginCommand cmd);
}
