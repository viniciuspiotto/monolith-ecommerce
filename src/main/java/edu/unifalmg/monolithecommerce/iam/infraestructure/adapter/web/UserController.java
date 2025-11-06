package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.web;

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
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security.utils.TokenExtractor;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
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
    private final TokenExtractor tokenExtractor;

    @PostMapping
    @PermitAll
    public ResponseEntity<?> createUser(
            @Valid @RequestBody CreateUserRequest createUserRequest
    ){
        CreateUserCommand cmd = userRequestMapper.toCommand(createUserRequest);
        UserDTO userdto = createUserCaseUse.execute(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(userdto);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ARTIST')")
    @PutMapping
    public ResponseEntity<?> updateUserInformation(
            HttpServletRequest request,
            @Valid @RequestBody UpdateUserInformationRequest updateUserInformationRequest
    ){
        String token = tokenExtractor.recoveryToken(request);
        UpdateUserInformationCommand cmd = updateUserInformationMapper.toCommand(token, updateUserInformationRequest);
        UpdateUserInformationDTO userDto = updateUserInformationCaseUse.execute(cmd);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ARTIST')")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(
            HttpServletRequest request
    ){
        String token = tokenExtractor.recoveryToken(request);
        DeleteUserCommand cmd = new DeleteUserCommand(token);
        Boolean deleted = deleteUserPort.execute(cmd);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ARTIST')")
    @PutMapping("password")
    public ResponseEntity<?> updateUserPassword(
            HttpServletRequest request,
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest
    ){
        String token = tokenExtractor.recoveryToken(request);
        UpdatePasswordCommand cmd = updatePasswordMapper.toCommand(token, updatePasswordRequest);
        Boolean isUpdated = updatePasswordCaseUse.execute(cmd);

        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ARTIST')")
    @PutMapping("/email")
    public ResponseEntity<?> updateEmailPassword(
            HttpServletRequest request,
            @Valid @RequestBody UpdateEmailRequest updateEmailRequest
    ){
        String token = tokenExtractor.recoveryToken(request);
        UpdateEmailCommand cmd = updateEmailMapper.toCommand(token, updateEmailRequest);
        Boolean isUpdated = updateEmailCaseUse.execute(cmd);

        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
