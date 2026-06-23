package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Role;
import com.susol.susolstudy.model.entity.StudyMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
    private int studyMemberId;
    private String memberName;
    private Role role;

    public static MemberResponseDTO entityOf(StudyMember studyMember){
        return new MemberResponseDTO(studyMember.getStudyMemberId(), studyMember.getUser().getUserName(), studyMember.getRole());
    }

}
