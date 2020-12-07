package com.poly.petcare.app.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfileResponses {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private Date birthDay;
    private String image;
    private String gender;
}
