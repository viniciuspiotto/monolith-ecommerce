package edu.unifalmg.monolithecommerce.iam.domain.model;

import edu.unifalmg.monolithecommerce.iam.domain.model.vo.Address;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.NationalId;
import edu.unifalmg.monolithecommerce.iam.domain.model.vo.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class User {

    private final UserId userId;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Address address;
    private UUID roleId;
    private NationalId nationalId;

    public static User create(String name, String lastName, String email, String password, UUID roleId, Address address, NationalId nationalId) {

        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if(lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException("Last cannot be null or empty");
        }

        isValidEmail(email);

        isValidPassword(password);

        if(roleId == null){
            throw new IllegalArgumentException("Role cannot be null");
        }

        if(address == null){
            throw new IllegalArgumentException("Address cannot be null.");
        }

        if(nationalId == null){
            throw new IllegalArgumentException("National Id cannot be null.");
        }

        return User.builder()
                .name(name)
                .lastName(lastName)
                .email(email)
                .password(password)
                .roleId(roleId)
                .address(address)
                .nationalId(nationalId)
                .build();
    }

    public static void isValidEmail(String email){
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new IllegalArgumentException("Email address is not in a valid format");
        }
    }
    public static void isValidPassword(String password){
        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new IllegalArgumentException("Password is not strong enough");
        }
    }
}
