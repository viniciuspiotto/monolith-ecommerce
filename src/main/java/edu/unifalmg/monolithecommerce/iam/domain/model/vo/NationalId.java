package edu.unifalmg.monolithecommerce.iam.domain.model.vo;

import edu.unifalmg.monolithecommerce.iam.domain.model.enums.NationalIdType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NationalId {

    private final NationalIdType type;
    private final String number;

    public static NationalId create (String number, NationalIdType type) {
        String nationalNumber = number.replaceAll("\\D", "");
        return new NationalId(type, nationalNumber);
    }

    private static boolean isValidCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")){
            return false;
        }
        return true;
    }


}
