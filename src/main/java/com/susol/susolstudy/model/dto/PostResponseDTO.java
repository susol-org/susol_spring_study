package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Post;
import com.susol.susolstudy.model.entity.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostResponseDTO {
    private int postId;
    private String postWriter; //게시판 작성자
    private String postType;
    private String postTitle;
    private int postViewCount;
    private LocalDateTime postCreatedAt;

    public static PostResponseDTO entityOf(Post post) {
        return new PostResponseDTO(
            post.getPostId(),
            post.getUser().getUserEmailId(),
            post.getPostType().name(),
            post.getPostTitle(),
            post.getPostViewCount(),
            post.getPostCreatedAt()
        );
    }
}
