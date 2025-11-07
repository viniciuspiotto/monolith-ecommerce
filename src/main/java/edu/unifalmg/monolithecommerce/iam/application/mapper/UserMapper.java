package edu.unifalmg.monolithecommerce.iam.application.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Address;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Email;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.RoleId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "userId.id", target = "userId")
    @Mapping(source = "nationalId.nationalNumber", target = "nationalId")
    @Mapping(source = "role", target = "role")
    UserDTO toDTO(User user);
    UserDTO.RoleDTO toDTORole(Role role);
    UserDTO.AddressDTO toAddressDTO(Address address);

    default String map(Email email) {
        if(email == null){
            return null;
        }
        return email.getEmail();
    }

    default UUID map(RoleId roleId) {
        if (roleId == null) {
            return null;
        }
        return roleId.id();
    }

}
