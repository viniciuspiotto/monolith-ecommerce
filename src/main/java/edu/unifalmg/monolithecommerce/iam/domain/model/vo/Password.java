package edu.unifalmg.monolithecommerce.iam.domain.model.vo;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Password {

    private String password;

    public static Password create(String password){
        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new IllegalArgumentException("Password is not strong enough");
        }
        return Password.builder()
                .password(password)
                .build();
    }

}
