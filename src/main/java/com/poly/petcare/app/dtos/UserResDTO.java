package com.poly.petcare.app.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poly.petcare.domain.entites.Role;
import com.poly.petcare.domain.entites.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResDTO {
    protected long id;
    protected String username;
    protected String password;
    protected Set<RoleDTO> role;

    public UserResDTO(User us) {
        this.id = us.getId();
        this.username = us.getUserName();
        this.password = us.getPassWord();
        Set<RoleDTO> roleDTOS = new HashSet<>();
        for (Role ro: us.getRoles()) {
            roleDTOS.add(new RoleDTO(ro));
        }
        this.role = roleDTOS;
    }
}
