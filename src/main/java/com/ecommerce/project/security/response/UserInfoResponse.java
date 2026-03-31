package com.ecommerce.project.security.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserInfoResponse {
    private Long  id;
    private String username;
    private String jwtToken;
    private List<String> roles;

    public UserInfoResponse(Long id, String jwtToken, List<String> roles, String username) {
        this.id = id;
        this.jwtToken = jwtToken;
        this.roles = roles;
        this.username = username;
    }

    public UserInfoResponse(Long id, List<String> roles, String username) {
        this.id = id;
        this.roles = roles;
        this.username = username;
    }
}
