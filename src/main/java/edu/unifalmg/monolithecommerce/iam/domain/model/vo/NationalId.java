package edu.unifalmg.monolithecommerce.iam.domain.model.vo;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.sun.jdi.request.InvalidRequestStateException;
import edu.unifalmg.monolithecommerce.iam.domain.model.enums.NationalIdType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NationalId {

    private final NationalIdType type;
    private final String nationalNumber;

    public static NationalId create (String number) {
        String nationalNumber = number.replaceAll("\\D", "");
        if(!isValid(nationalNumber)){
            throw new IllegalArgumentException("The National number is not valid");
        }
        return new NationalId(getType(nationalNumber), nationalNumber);
    }

    private static NationalIdType getType(String number){
        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("The National number is not with the correct type");
        }
        if(number.length() == 11){
            return NationalIdType.CPF;
        }
        if(number.length() == 14){
            return NationalIdType.CNPJ;
        }
        throw new IllegalArgumentException("The National number is not with the correct type");
    }

    public static boolean isValid(String number) {

        if (number == null || number.isEmpty()) {
            return false;
        }

        CPFValidator cpfValidator = new CPFValidator();
        CNPJValidator cnpjValidator = new CNPJValidator();

        try {
            cpfValidator.assertValid(number);
            return true;
        } catch (InvalidRequestStateException e1) {
            try {
                cnpjValidator.assertValid(number);
                return true;
            } catch (InvalidStateException e2) {
                return false;
            }
        }
    }

}
