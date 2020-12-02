package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private Long id;
    @NotBlank(message = "Cannot to blank field userName")
    @Size(min = 3, max = 20)
    private String userName;

    @NotBlank(message = "Cannot to blank field passWord")
    @Size(min = 3, max = 20)
    private String passWord;
    private Long profileID;
    private Long roleID;
    private String status;
}
