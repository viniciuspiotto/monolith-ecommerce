package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateEmailCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.UpdateEmailPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.TokenUtilsPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateEmailUseCase implements UpdateEmailPort {

    private final UserRepositoryPort userRepository;
    private final TokenUtilsPort tokenUtils;

    @Override
    @Transactional
    public Boolean execute(UpdateEmailCommand cmd){

        String email = tokenUtils.extractEmail(cmd.token());
        User user = userRepository.findByEmail(email);

        if (!user.getEmail().getEmail().equals(cmd.oldEmail())) {
            throw new IllegalArgumentException("The email doesn't match the previous version.");
        }

        Email newEmail = new Email(cmd.newEmail());
        user.updateEmail(newEmail);
        userRepository.update(user);

        return true;

    }
}
