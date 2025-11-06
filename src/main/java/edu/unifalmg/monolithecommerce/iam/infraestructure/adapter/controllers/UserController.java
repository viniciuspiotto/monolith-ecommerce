package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.controllers;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UpdateUserInformationDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.*;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.CreateUserRequest;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.UpdateEmailRequest;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.UpdatePasswordRequest;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.UpdateUserInformationRequest;
import edu.unifalmg.monolithecommerce.iam.application.port.in.*;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.UpdateEmailRequestMapper;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.UpdatePasswordRequestMapper;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.UpdateUserInformationRequestMapper;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.UserRequestMapper;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Log4j2
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserPort createUserCaseUse;
    private final UserRequestMapper userRequestMapper;
    private final UpdateUserInformationPort updateUserInformationCaseUse;
    private final UpdateUserInformationRequestMapper updateUserInformationMapper;
    private final UpdatePasswordPort updatePasswordCaseUse;
    private final UpdatePasswordRequestMapper updatePasswordMapper;
    private final UpdateEmailPort updateEmailCaseUse;
    private final UpdateEmailRequestMapper updateEmailMapper;
    private final DeleteUserPort deleteUserPort;

    @PostMapping
    @PermitAll
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody CreateUserRequest createUserRequest
    ){
        log.info("Received request to create user for email: {}", createUserRequest.email());
        CreateUserCommand cmd = userRequestMapper.toCommand(createUserRequest);
        UserDTO userdto = createUserCaseUse.execute(cmd);
        log.info("User created successfully with ID: {}", userdto.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userdto);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ARTIST')")
    @PutMapping
    public ResponseEntity<UpdateUserInformationDTO> updateUserInformation(
            Principal principal,
            @Valid @RequestBody UpdateUserInformationRequest updateUserInformationRequest
    ){
        String authenticatedUserEmail = principal.getName();
        log.info("User {} initiated information update.", authenticatedUserEmail);

        UpdateUserInformationCommand cmd = updateUserInformationMapper.toCommand(authenticatedUserEmail, updateUserInformationRequest);
        UpdateUserInformationDTO userDto = updateUserInformationCaseUse.execute(cmd);

        log.info("User {} information updated successfully.", authenticatedUserEmail);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ARTIST')")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(
            Principal principal
    ){
        String authenticatedUserEmail = principal.getName();
        log.info("User {} initiated account deletion.", authenticatedUserEmail);

        DeleteUserCommand cmd = new DeleteUserCommand(authenticatedUserEmail);
        deleteUserPort.execute(cmd);

        log.info("User {} deleted successfully.", authenticatedUserEmail);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ARTIST')")
    @PutMapping("password")
    public ResponseEntity<?> updateUserPassword(
            Principal principal,
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest
    ){
        String authenticatedUserEmail = principal.getName();
        log.info("User {} initiated password update.", authenticatedUserEmail);

        UpdatePasswordCommand cmd = updatePasswordMapper.toCommand(authenticatedUserEmail, updatePasswordRequest);
        Boolean isUpdated = updatePasswordCaseUse.execute(cmd);

        if(isUpdated){
            log.info("User {} password updated successfully.", authenticatedUserEmail);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            log.warn("Failed to update password for user {}. Bad request.", authenticatedUserEmail);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ARTIST')")
    @PutMapping("/email")
    public ResponseEntity<?> updateUserEmail(
            Principal principal,
            @Valid @RequestBody UpdateEmailRequest updateEmailRequest
    ){
        String authenticatedUserEmail = principal.getName();
        log.info("User {} initiated email update.", authenticatedUserEmail);

        UpdateEmailCommand cmd = updateEmailMapper.toCommand(authenticatedUserEmail, updateEmailRequest);
        Boolean isUpdated = updateEmailCaseUse.execute(cmd);

        if(isUpdated){
            log.info("User {} email updated successfully.", authenticatedUserEmail);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            log.warn("Failed to update email for user {}. Bad request.", authenticatedUserEmail);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
