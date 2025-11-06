package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.web;

import edu.unifalmg.monolithecommerce.iam.application.DTO.RoleDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.CreateRoleCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.GetRoleByIdCommand;
import edu.unifalmg.monolithecommerce.iam.application.DTO.requests.CreateRoleRequest;
import edu.unifalmg.monolithecommerce.iam.application.port.in.CreateRolePort;
import edu.unifalmg.monolithecommerce.iam.application.port.in.GetRoleByIdPort;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.mapper.RoleRequestMapper;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final GetRoleByIdPort getRoleByIdUserCase;
    private final CreateRolePort createRoleUserCase;
    private final RoleRequestMapper roleRequestMapper;

    @PostMapping
    @PermitAll
    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<?> createRole(
            @Valid @RequestBody CreateRoleRequest request
    ) {
        CreateRoleCommand cmd = roleRequestMapper.toCommand(request);
        RoleDTO createdCategory = createRoleUserCase.execute(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PreAuthorize("hasRole('ARTIST')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(
            @PathVariable("id") UUID id
    ) {
        GetRoleByIdCommand command = new GetRoleByIdCommand(id);
        RoleDTO fundedCategory = getRoleByIdUserCase.execute(command);
        return ResponseEntity.status(HttpStatus.OK).body(fundedCategory);
    }

}
