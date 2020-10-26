package com.poly.petcare.app.responses;

import com.poly.petcare.domain.entites.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponses {
    private String userName;
    private List<Profile> profiles;
    private String fullName;
    private String email;
}
