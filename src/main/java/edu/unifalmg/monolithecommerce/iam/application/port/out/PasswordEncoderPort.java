package edu.unifalmg.monolithecommerce.iam.application.port.out;

public interface PasswordEncoderPort {
    public String encode(String password);
    public Boolean matches(String oldPassword, String newPassword);
}
