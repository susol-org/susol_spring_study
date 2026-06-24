package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.StudyMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinStudyResponseDTO {
    private int studyId;
    private String studyTitle;

    public static JoinStudyResponseDTO entityOf(StudyMember studyMember) {
        return new JoinStudyResponseDTO(
            studyMember.getStudy().getStudyId(),
            studyMember.getStudy().getStudyTitle()
        );
    }
}
