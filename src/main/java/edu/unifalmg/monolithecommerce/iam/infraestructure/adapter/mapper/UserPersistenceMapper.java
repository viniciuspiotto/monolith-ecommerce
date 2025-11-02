package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper;

import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.*;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.RoleEntity;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.UserEntity;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.embeddable.AddressEmbeddable;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.embeddable.NationalIdEmbeddable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    @Mapping(source = "userId.id", target = "id")
    @Mapping(source = "email.email", target = "email")
    @Mapping(source = "password.password", target = "password")
    @Mapping(source = "role", target = "roleId")
    UserEntity toEntity(User user);

    AddressEmbeddable addressToEmbeddable(Address address);
    NationalIdEmbeddable nationalIdToEmbeddable(NationalId nationalId);

    default RoleEntity roleToEntity(Role role) {
        return new RoleEntity(role.getRoleId().id(), role.getName(), role.getDescription());
    }

    default User toDomain(UserEntity entity) {

        if(entity == null) {
            return null;
        }

        UserId userid = mapToUser(entity.getId());
        Password password = mapToPassword(entity.getPassword());
        Email email = mapToEmail(entity.getEmail());
        NationalId nationalId = map(entity.getNationalId());
        Role role = map(entity.getRoleId());
        Address address = map(entity.getAddress());

        return User.rehydrate(
                userid,
                entity.getName(),
                entity.getLastName(),
                email,
                password,
                role,
                address,
                nationalId
        );

    }

    default UserId mapToUser (UUID id){
        return new UserId(id);
    }

    default Password mapToPassword(String password){
        return new Password(password);
    }

    default Email mapToEmail(String email){
        return new Email(email);
    }

    default NationalId map(NationalIdEmbeddable embeddable){
        return NationalId.create(embeddable.getNationalNumber());
    }

    default Role map(RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        return Role.rehydrate(new RoleId(entity.getId()), entity.getName(), entity.getDescription());
    }

    default Address map(AddressEmbeddable addressEmbeddable){
        return Address.create(
                addressEmbeddable.getCountry(),
                addressEmbeddable.getCity(),
                addressEmbeddable.getState(),
                addressEmbeddable.getZip(),
                addressEmbeddable.getStreet(),
                addressEmbeddable.getNumber(),
                addressEmbeddable.getNeighborhood(),
                addressEmbeddable.getComplement()
        );
    }

}
