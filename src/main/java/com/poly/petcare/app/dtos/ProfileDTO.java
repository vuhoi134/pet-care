package com.poly.petcare.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfileDTO {
    private Long id;

    @NotBlank
    @Size(min = 5, max = 100)
    private String fullName;

    @NotBlank
    @Size(min = 9, max = 11)
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 50)
    private String address;

    private Date birthDay;
    @NotBlank

    private String images;
    @NotBlank
    private String gender;
}
