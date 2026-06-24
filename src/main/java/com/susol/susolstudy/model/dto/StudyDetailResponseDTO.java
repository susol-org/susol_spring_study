package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Category;
import com.susol.susolstudy.model.entity.RecruitStatus;
import com.susol.susolstudy.model.entity.Study;
import com.susol.susolstudy.model.entity.StudyMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyDetailResponseDTO {
    private int studyId;
    private String studyTitle;
    private String studyDescription;
    private int currentMemberCount;
    private int maxMemberCount;
    private Category category;
    private RecruitStatus recruitStatus;
    private LocalDateTime studyEndDate;
    private List<MemberResponseDTO> members;

    public static StudyDetailResponseDTO entityOf(Study study, List<StudyMember> memberList) {
        List<MemberResponseDTO> members = memberList.stream()
                .map(MemberResponseDTO::entityOf)
                .toList();

        return new StudyDetailResponseDTO(
                study.getStudyId(),
                study.getStudyTitle(),
                study.getStudyDescription(),
                study.getCurrentMemberCount(),
                study.getMaxMemberCount(),
                study.getCategory(),
                study.getRecruitStatus(),
                study.getStudyEndDate(),
                members
        );
    }
}
