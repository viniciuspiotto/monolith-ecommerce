package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence;

import edu.unifalmg.monolithecommerce.iam.application.port.out.UserRepositoryPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.UserPersistenceMapper;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.UserEntity;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security.utils.PasswordEncoderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserPersistenceMapper userPersistenceMapper;
    private final UserJpaRepository JpaRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Override
    public User save(User user) {
        UserEntity entityToSave = userPersistenceMapper.toEntity(user);
        entityToSave.setPassword(passwordEncoderConfig.passwordEncoder().encode(entityToSave.getPassword()));
        UserEntity savedEntity = JpaRepository.save(entityToSave);

        return userPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public User updatePassword (User user) {
        UserEntity entityToSave = userPersistenceMapper.toEntity(user);
        entityToSave.setPassword(passwordEncoderConfig.passwordEncoder().encode(entityToSave.getPassword()));
        UserEntity savedEntity = JpaRepository.save(entityToSave);
        return userPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public User update (User user) {
        UserEntity entityToSave = userPersistenceMapper.toEntity(user);
        UserEntity savedEntity = JpaRepository.save(entityToSave);
        return userPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return JpaRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email){
        Optional<UserEntity> entityFind = JpaRepository.findByEmail(email);
        if(entityFind.isEmpty()){
            throw new RuntimeException("User with this email not found");
        }
        return userPersistenceMapper.toDomain(entityFind.get());

    }

    @Override
    public Boolean delete (User user){
        UserEntity entityToDelete = userPersistenceMapper.toEntity(user);
        JpaRepository.delete(entityToDelete);
        return true;
    }

}
