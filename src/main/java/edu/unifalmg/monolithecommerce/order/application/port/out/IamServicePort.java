package edu.unifalmg.monolithecommerce.order.application.port.out;

import edu.unifalmg.monolithecommerce.iam.infraestructure.api.UserInformationDTO;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.Payer;

import java.util.UUID;

public interface IamServicePort {
    UserInformationDTO getUserInformationById(UUID userId);
}
