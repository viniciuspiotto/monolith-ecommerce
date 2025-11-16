package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.infraestructure.api.GetUserInformationByIdPort;
import edu.unifalmg.monolithecommerce.iam.infraestructure.api.UserInformationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserInformationByIdUseCase implements GetUserInformationByIdPort {

    private final UserRepositoryPort userRepository;

    @Override
    public UserInformationDTO execute(UUID userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new IllegalArgumentException("A user with this email does not exist");
        }
        return new UserInformationDTO(user.get().getName(), user.get().getEmail().getEmail());
    }
}
