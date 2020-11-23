package com.poly.petcare.app.dtos;

import com.poly.petcare.domain.entites.Role;
import lombok.Data;

@Data
public class RoleDTO {
    private Long id;

    private String name;

    public RoleDTO(Role role) {
        this.id=role.getId();
        this.name = role.getName();
    }
}
