package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyRequestDTO {
    private String studyTitle;
    private String studyDescription;
    private int maxMemberCount;
    private LocalDateTime studyEndDate;
    private Category category;
}
