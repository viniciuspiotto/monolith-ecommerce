package edu.unifalmg.monolithecommerce.iam.domain.model.vo;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class HashedPassword {

    private String hashedPassword;

    public static HashedPassword create(String password){
        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return HashedPassword.builder()
                .hashedPassword(password)
                .build();
    }
}
