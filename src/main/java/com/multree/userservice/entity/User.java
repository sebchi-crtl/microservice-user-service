package com.multree.userservice.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID,
            generator = "user_id_sequence"
    )
    private UUID user_id;
    @Size(max = 25, message = "value should not be more than 35 in length")
    private String organization_name;
    private String email;
    @ElementCollection(targetClass = RoleUser.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_id_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Set<RoleUser> role;
    private boolean active;
    private boolean verify;
    //    @Pattern(regexp = "^([0]{1})[0-9]{10}$", message = "please enter a valid nigeria number")
    private String phone_number;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Transient
    private String token;
}
