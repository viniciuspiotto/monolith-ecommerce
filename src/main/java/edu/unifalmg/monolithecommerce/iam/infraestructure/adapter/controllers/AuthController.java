package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.controllers;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.LoginCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.LoginRequest;
import edu.unifalmg.monolithecommerce.iam.application.mapper.UserMapper;
import edu.unifalmg.monolithecommerce.iam.application.port.in.LoginPort;
import edu.unifalmg.monolithecommerce.iam.domain.model.User;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.LoginRequestMapper;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security.TokenGenerationService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("api/v1/auth")
@Log4j2
@RequiredArgsConstructor
public class AuthController {

    private final LoginPort loginUserCase;
    private final LoginRequestMapper loginRequestMapper;
    private final UserMapper userMapper;
    private final TokenGenerationService tokenGenerationService;

    @Value("${api.security.cookie.name}")
    private String jwtCookieName;

    @Value("${api.security.cookie.secure}")
    private boolean secureCookie;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Authentication attempt for user: {}", loginRequest.email());

        LoginCommand loginCommand = loginRequestMapper.toCommand(loginRequest);
        User authenticatedUser = loginUserCase.execute(loginCommand);
        String token = tokenGenerationService.generateToken(authenticatedUser);

        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, token)
                .httpOnly(true)
                .secure(secureCookie)
                .path("/")
                .maxAge(Duration.ofHours(24))
                .sameSite("Strict")
                .build();

        log.info("User {} authenticated. HttpOnly cookie set.", authenticatedUser.getEmail().getEmail());

        UserDTO userDto = userMapper.toDTO(authenticatedUser);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(userDto);
    }

    @PostMapping("/logout")
    @PermitAll
    public ResponseEntity<?> logout() {
        log.info("User logout request received. Clearing cookie.");

        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, "")
                .httpOnly(true)
                .secure(secureCookie)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logout successful");
    }
}
