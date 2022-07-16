package com.prices.model.dto;

import com.prices.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserRequestDTO {
    private Long id;
    private String email;
    private String name;
    private String lastName;
    private String password;
    private boolean active;
    private Set<Role> roles;

}
