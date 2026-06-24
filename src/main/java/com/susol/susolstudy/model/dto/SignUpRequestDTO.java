package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SignUpRequestDTO {
    private String userEmailId;
    private String userPassword;
    private String userName;
    private int userAge;
    private Gender gender;
    private String userShortBio;
}
