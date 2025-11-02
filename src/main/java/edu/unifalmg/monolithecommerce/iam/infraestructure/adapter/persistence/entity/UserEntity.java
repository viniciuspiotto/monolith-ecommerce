package edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity;

import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.embeddable.AddressEmbeddable;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.persistence.entity.embeddable.NationalIdEmbeddable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "iam_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId", nullable = false)
    private RoleEntity roleId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.roleId.getName().toUpperCase()));
    }

    public String getUsername() {
        return this.email;
    }

}
