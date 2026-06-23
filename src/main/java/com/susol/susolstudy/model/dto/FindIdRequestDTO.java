package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FindIdRequestDTO {
    private String userName;
    private int userAge;
    private Gender userGender;
}
