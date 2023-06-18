package com.multree.userservice.response;

import com.multree.userservice.entity.RoleUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationResponse {
    private String organization_name;
    private String email;
    private Set<RoleUser> role;
    private String phone_number;
}
