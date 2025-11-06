package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security.utils;

import edu.unifalmg.monolithecommerce.iam.application.port.out.TokenUtilsPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.UserPersistenceMapper;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTUtilAdapter implements TokenUtilsPort {

    private final JWTUtil jwtUtil;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public String generateToken(User user){
        UserEntity entityToCreateToken = userPersistenceMapper.toEntity(user);
        return jwtUtil.generateToken(entityToCreateToken);
    }

    @Override
    public String extractEmail(String token){
        return jwtUtil.extractUsername(token);
    }

}
