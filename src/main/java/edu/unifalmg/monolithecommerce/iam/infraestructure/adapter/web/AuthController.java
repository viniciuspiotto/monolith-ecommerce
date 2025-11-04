package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.web;

import edu.unifalmg.monolithecommerce.iam.application.DTO.LoginDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.LoginCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.LoginRequest;
import edu.unifalmg.monolithecommerce.iam.application.port.in.LoginPort;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.LoginRequestMapper;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginPort loginUserCase;
    private final LoginRequestMapper loginRequestMapper;

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        LoginCommand loginCommand = loginRequestMapper.toCommand(loginRequest);
        LoginDTO loginDTO = loginUserCase.execute(loginCommand);

        ResponseCookie cookie = ResponseCookie.from("jwt-token", loginDTO.token())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Bearer " + loginDTO.token());
    }

    @PostMapping("/logout")
    @PermitAll
    public ResponseEntity<?> logout() {

        ResponseCookie cookie = ResponseCookie.from("jwt-token", "deleted")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
