package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostUpdateResponseDTO {
    private String postType;
    private String postTitle;
    private String postContent;

    public static PostUpdateResponseDTO entityOf(Post post) {
        return new PostUpdateResponseDTO(
            post.getPostType().name(),
            post.getPostTitle(),
            post.getPostContent()
        );
    }
}
