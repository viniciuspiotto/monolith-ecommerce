package edu.unifalmg.monolithecommerce.iam.application.port.in;

import edu.unifalmg.monolithecommerce.iam.application.DTO.LoginDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.LoginCommand;

public interface LoginPort {
    LoginDTO execute(LoginCommand cmd);
}
