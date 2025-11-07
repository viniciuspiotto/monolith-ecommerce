package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence;

import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.UserPersistenceMapper;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.UserEntity;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserPersistenceMapper userPersistenceMapper;
    private final UserJpaRepository jpaRepository;

    @Override
    public User save(User user) {
        UserEntity entityToSave = userPersistenceMapper.toEntity(user);
        UserEntity savedEntity = jpaRepository.save(entityToSave);
        return userPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        Optional<UserEntity> entityFind = jpaRepository.findByEmail(email);

        if (entityFind.isEmpty()) {
            throw new ResourceNotFoundException("User with email " + email + " not found.");
        }
        return userPersistenceMapper.toDomain(entityFind.get());
    }

    @Override
    public Boolean delete(User user) {
        UserEntity entityToDelete = userPersistenceMapper.toEntity(user);
        jpaRepository.delete(entityToDelete);
        return true;
    }
}

