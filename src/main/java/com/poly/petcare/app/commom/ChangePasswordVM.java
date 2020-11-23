package com.poly.petcare.app.commom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChangePasswordVM {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
