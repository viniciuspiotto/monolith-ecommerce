package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security.utils;

import edu.unifalmg.monolithecommerce.iam.application.port.out.PasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoderConfig passwordEncoder;

    @Override
    public String encode (String password){
        return passwordEncoder.passwordEncoder().encode(password);
    }

    @Override
    public Boolean matches (String oldPassword, String newPassword){
        return passwordEncoder.passwordEncoder().matches(oldPassword, newPassword);
    }

}
