package edu.unifalmg.monolithecommerce.iam.domain.model;

import edu.unifalmg.monolithecommerce.iam.domain.model.vo.RoleId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Role {
    private RoleId roleId;
    private String name;
    private String description;

    public static Role create(String name, String description) {
        if(name == null){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if(description == null){
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        return Role.builder()
                .roleId(new RoleId(UUID.randomUUID()))
                .name(name)
                .description(description)
                .build();
    }

    public static Role rehydrate(
            RoleId roleId,
            String name,
            String description
    ) {
        return Role.builder()
                .roleId(roleId)
                .name(name)
                .description(description)
                .build();
    }
}
