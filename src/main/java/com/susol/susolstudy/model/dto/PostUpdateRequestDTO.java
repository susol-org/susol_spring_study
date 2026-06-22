package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostUpdateRequestDTO {
    private PostType postType;
    private String postTitle;
    private String postContent;
}
