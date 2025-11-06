package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdateEmailCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.UpdateEmailPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UpdateEmailUseCase implements UpdateEmailPort {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Boolean execute(UpdateEmailCommand cmd){
        String authenticatedEmail = cmd.email();
        log.info("Initiating email update for user: {}", authenticatedEmail);

        User user = userRepository.findByEmail(authenticatedEmail);

        if (!passwordEncoder.matches(cmd.passwordToConfirm(), user.getHashedPassword().getHashedPassword())) {
            log.warn("Email update failed for {}: Invalid password.", authenticatedEmail);
            throw new IllegalArgumentException("Invalid password. Email update denied.");
        }

        if (userRepository.existsByEmail(cmd.newEmail())) {
            log.warn("Email update failed for {}: New email {} already in use.", authenticatedEmail, cmd.newEmail());
            throw new IllegalArgumentException("The email " + cmd.newEmail() + " is already in use by another account.");
        }

        Email newEmail = Email.create(cmd.newEmail());

        user.updateEmail(newEmail);
        userRepository.save(user);

        log.info("Email updated successfully for user {}. New email is {}.", authenticatedEmail, cmd.newEmail());
        return true;
    }
}
