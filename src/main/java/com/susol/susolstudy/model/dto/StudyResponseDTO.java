package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.RecruitStatus;
import com.susol.susolstudy.model.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyResponseDTO {
    private int studyId;
    private String studyTitle;
    private String studyDescription;
    private int currentMemberCount;
    private int maxMemberCount;
    private RecruitStatus recruitStatus;

    public static StudyResponseDTO entityOf(Study study) {
        return new StudyResponseDTO(
                study.getStudyId(),
                study.getStudyTitle(),
                study.getStudyDescription(),
                study.getCurrentMemberCount(),
                study.getMaxMemberCount(),
                study.getRecruitStatus()
        );
    }
}
