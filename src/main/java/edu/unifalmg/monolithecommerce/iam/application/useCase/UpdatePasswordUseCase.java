package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdatePasswordCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.UpdatePasswordPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.HashedPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UpdatePasswordUseCase implements UpdatePasswordPort {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String PASSWORD_STRENGTH_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    @Override
    @Transactional
    public Boolean execute(UpdatePasswordCommand cmd){
        String email = cmd.email();
        log.info("Initiating password update for user: {}", email);

        User user = userRepository.findByEmail(email);

        if (!passwordEncoder.matches(cmd.oldPassword(), user.getHashedPassword().getHashedPassword())) {
            log.warn("Password update failed for {}: Old password does not match.", email);
            throw new IllegalArgumentException("The provided old password doesn't match the current password.");
        }

        if (!cmd.newPassword().equals(cmd.newConfirmPassword())) {
            log.warn("Password update failed for {}: New passwords do not match.", email);
            throw new IllegalArgumentException("The new password and confirmation password do not match.");
        }

        validatePasswordStrength(cmd.newPassword(), email);

        String encodedNewPassword = passwordEncoder.encode(cmd.newPassword());

        HashedPassword hashedPassword = HashedPassword.create(encodedNewPassword);
        user.updateHashedPassword(hashedPassword);
        userRepository.save(user);

        log.info("Password updated successfully for user: {}", email);
        return true;
    }

    private void validatePasswordStrength(String plaintextPassword, String email) {
        if (!plaintextPassword.matches(PASSWORD_STRENGTH_REGEX)) {
            log.warn("Password update failed for {}: New password does not meet strength requirements.", email);
            throw new IllegalArgumentException("Password is not strong enough. It must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one number, and one special character.");
        }
        log.debug("Password strength validation passed for user {}", email);
    }
}
