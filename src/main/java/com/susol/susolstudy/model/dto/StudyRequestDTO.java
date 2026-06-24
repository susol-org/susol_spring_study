package com.susol.susolstudy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyRequestDTO {
    private String studyTitle;
    private String studyDescription;
    private int maxMemberCount;
    private LocalDateTime studyEndDate;



}
