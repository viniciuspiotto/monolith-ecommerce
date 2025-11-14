package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api;

import edu.unifalmg.monolithecommerce.iam.infraestructure.api.GetUserInformationByIdPort;
import edu.unifalmg.monolithecommerce.iam.infraestructure.api.UserInformationDTO;
import edu.unifalmg.monolithecommerce.order.application.port.out.IamServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IamServiceAdapter implements IamServicePort {

    private final GetUserInformationByIdPort getUserEmailByIdPort;

    @Override
    public UserInformationDTO getUserInformationById(UUID userId) { return getUserEmailByIdPort.execute(userId);}

}
