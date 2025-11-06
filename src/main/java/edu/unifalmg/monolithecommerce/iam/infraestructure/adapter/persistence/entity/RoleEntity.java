package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity;

import edu.unifalmg.monolithecommerce.iam.domain.model.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.Id;


import java.util.UUID;

@Entity
@Table(name = "iam_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;

    public RoleEntity (UUID id){
        this.id = id;
    }


}
