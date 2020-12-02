package com.poly.petcare.app.dtos;

import com.poly.petcare.domain.entites.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WearHouseDTO {
    private String code;
    private String address;
    private User user;
    private Long userID;
}
