package com.susol.susolstudy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostFileDTO {
    private String originalFileName;
    private String renamedFileName;
}
