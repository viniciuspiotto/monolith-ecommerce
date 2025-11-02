package edu.unifalmg.monolithecommerce.iam.application.useCase;

import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.UpdatePasswordCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.UpdatePasswordPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.PasswordEncoderPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.TokenUtilsPort;
import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Password;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePasswordUseCase implements UpdatePasswordPort {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenUtilsPort tokenUtils;

    @Override
    @Transactional
    public Boolean execute(UpdatePasswordCommand cmd){

        String email = tokenUtils.extractEmail(cmd.token());
        User user = userRepository.findByEmail(email);

        if (passwordEncoder.matches(user.getPassword().getPassword(), cmd.oldPassword())) {
            throw new IllegalArgumentException("The password doesn't match the previous version.");
        }

        if(!cmd.newPassword().equals(cmd.newConfirmPassword())){
            throw new IllegalArgumentException("The passwords don't match");
        }

        Password password = new Password(cmd.newPassword());
        user.updatePassword(password);
        userRepository.updatePassword(user);

        return true;

    }
}
