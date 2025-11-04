package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.DeleteUserCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.DeleteUserPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.TokenUtilsPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase implements DeleteUserPort {

    private final UserRepositoryPort userRepository;
    private final TokenUtilsPort tokenUtils;

    @Override
    @Transactional
    public Boolean execute(DeleteUserCommand cmd){

        String email = tokenUtils.extractEmail(cmd.token());
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("Invalid credentials.");
        }

        userRepository.delete(user);
        return true;

    }
}
