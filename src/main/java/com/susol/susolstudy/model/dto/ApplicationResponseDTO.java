package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.ApplicationStatus;
import com.susol.susolstudy.model.entity.StudyApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDTO {
    private String username;
    private String message;
    private ApplicationStatus applicationStatus;

    public static ApplicationResponseDTO entityOf(StudyApplication studyApplication){
        return new ApplicationResponseDTO(studyApplication.getUser().getUserName(), studyApplication.getMessage(), studyApplication.getStatus());
    }
}


//Controller(조회 요청) → Service(지원자 목록 조회) → DTO 리스트로 변환 → 반환