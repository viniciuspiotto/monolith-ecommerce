package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.DeleteUserCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.DeleteUserPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeleteUserUseCase implements DeleteUserPort {

    private final UserRepositoryPort userRepository;

    @Override
    @Transactional
    public Boolean execute(DeleteUserCommand cmd){
        String email = cmd.email();
        log.info("Initiating account deletion for user: {}", email);

        User user = userRepository.findByEmail(email);

        userRepository.delete(user);
        log.info("User {} deleted successfully.", email);

        return true;
    }
}
