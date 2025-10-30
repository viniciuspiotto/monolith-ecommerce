package edu.unifalmg.monolithecommerce.iam.application.mapper;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "userId.id", target = "userId")
    @Mapping(source = "nationalId.number", target = "nationalId")
    UserDTO toDTO(User user);
    UserDTO.AddressDTO toAddressDTO(Address address);
}
