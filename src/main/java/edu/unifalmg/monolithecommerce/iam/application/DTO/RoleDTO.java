package edu.unifalmg.monolithecommerce.iam.application.DTO;

import java.util.UUID;

public record RoleDTO(
        UUID roleId,
        String name,
        String description
) {
}
