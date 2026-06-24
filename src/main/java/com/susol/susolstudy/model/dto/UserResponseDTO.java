package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Gender;
import com.susol.susolstudy.model.entity.Permission;
import com.susol.susolstudy.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserResponseDTO {
    private String userEmailId;
    private String userName;
    private int userAge;
    private Gender userGender;
    private String userShortBio;
    private Permission userPermission;

    public static UserResponseDTO entityOf(User user) {
        return new UserResponseDTO(
            user.getUserEmailId(),
            user.getUserName(),
            user.getUserAge(),
            user.getUserGender(),
            user.getUserShortBio(),
            user.getUserPermission()
        );
    }
}
