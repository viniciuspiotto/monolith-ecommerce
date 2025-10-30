package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity;

import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.embeddable.AddressEmbeddable;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.embeddable.NationalIdEmbeddable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "iam_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    @Embedded
    private AddressEmbeddable address;
    @Embedded
    private NationalIdEmbeddable nationalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", nullable = false)
    private RoleEntity roleId;
}
