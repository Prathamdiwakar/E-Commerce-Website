package com.ecommerce.project.security.request;

import com.ecommerce.project.Model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {

    @NotNull
    @Size(min =3, max=20)
    private String username;

    @NotNull
    @Size(max=50)
    @Email
    private String email;
    private Set<String> role;

    @NotNull
    @Size(min =6, max=40)
    private String password;
}
