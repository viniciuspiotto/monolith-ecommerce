package edu.unifalmg.monolithecommerce.iam.domain.model.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Email {

    private String email;

    public static Email create(String email){
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new IllegalArgumentException("Email address is not in a valid format");
        }
        return Email.builder()
                .email(email)
                .build();
    }
}
