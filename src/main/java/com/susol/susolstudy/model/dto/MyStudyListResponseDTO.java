package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Study;
import com.susol.susolstudy.model.entity.StudyMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyStudyListResponseDTO {
    private int study_id;

    public static MyStudyListResponseDTO entityOf(StudyMember studyMember) {
        return new MyStudyListResponseDTO(studyMember.getStudy().getStudyId());
    }
}
