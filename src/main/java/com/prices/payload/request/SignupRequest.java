package com.prices.payload.request;

import com.prices.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String repeatPassword;
    private Set<Role> roles;

    public SignupRequest() {
    }
}
