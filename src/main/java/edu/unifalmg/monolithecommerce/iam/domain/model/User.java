package edu.unifalmg.monolithecommerce.iam.domain.model;

import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Address;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class User {
    private final UserId userId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private Address address;
}
