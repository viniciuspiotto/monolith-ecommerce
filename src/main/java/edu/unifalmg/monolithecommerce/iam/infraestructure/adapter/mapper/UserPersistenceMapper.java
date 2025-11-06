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

    @Mapping(target = "id", source = "userId.id")
    @Mapping(target = "email", source = "email.email")
    @Mapping(target = "hashedPassword", source = "hashedPassword.hashedPassword")
    @Mapping(target = "roleId", source = "role")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "nationalId", source = "nationalId")
    UserEntity toEntity(User user);

    AddressEmbeddable addressToEmbeddable(Address address);
    NationalIdEmbeddable nationalIdToEmbeddable(NationalId nationalId);

    default RoleEntity roleToEntity(Role role) {
        if (role == null) return null;
        return new RoleEntity(role.getRoleId().id(), role.getName(), role.getDescription());
    }

    default User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserId userid = mapToUserId(entity.getId());
        HashedPassword hashedPassword = mapToPassword(entity.getPassword());
        Email email = mapToEmail(entity.getEmail());
        NationalId nationalId = mapToNationalId(entity.getNationalId());
        Role role = mapToRole(entity.getRoleId());
        Address address = mapToAddress(entity.getAddress());

        return User.rehydrate(
                userid,
                entity.getName(),
                entity.getLastName(),
                email,
                hashedPassword,
                role,
                address,
                nationalId
        );
    }

    default UserId mapToUserId(UUID id) {
        if (id == null) return null;
        return new UserId(id);
    }

    default HashedPassword mapToPassword(String hashedPassword) {
        if (hashedPassword == null) return null;
        return new HashedPassword(hashedPassword);
    }

    default Email mapToEmail(String email) {
        if (email == null) return null;
        return new Email(email);
    }

    default NationalId mapToNationalId(NationalIdEmbeddable embeddable) {
        if (embeddable == null) return null;
        return NationalId.rehydrate(embeddable.getNationalNumber());
    }

    default Role mapToRole(RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        return Role.rehydrate(new RoleId(entity.getId()), entity.getName(), entity.getDescription());
    }

    default Address mapToAddress(AddressEmbeddable addressEmbeddable) {
        if (addressEmbeddable == null) return null;
        return Address.rehydrate(
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
