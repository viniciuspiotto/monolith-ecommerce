package edu.unifalmg.monolithecommerce.iam.infraestructure.api;

import java.util.UUID;

public interface GetUserInformationByIdPort {
    UserInformationDTO execute(UUID userId);
}
