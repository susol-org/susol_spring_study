package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.Post;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostDetailResponseDTO {
    private String studyTitle;
    private String writerEmailId;
    private String postType;
    private String postTitle;
    private String postContent;
    private int postViewCount;
    private String postCreatedAt;
    private String postUpdatedAt;
    private List<PostFileDTO> postFiles = new ArrayList<>();

    public static PostDetailResponseDTO entityOf(Post post) {
        return new PostDetailResponseDTO(
                post.getStudy().getStudyTitle(),
                post.getUser().getUserEmailId(),
                post.getPostType().getKoreanName(),
                post.getPostTitle(),
                post.getPostContent(),
                post.getPostViewCount(),
                post.getPostCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                post.getPostUpdatedAt() != null ?
                        post.getPostUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null,
                post.getPostFiles()
                        .stream()
                        .map(f -> new PostFileDTO(f.getPostOriginalFileName(), f.getPostRenamedFileName()))
                        .toList()
        );
    }
}
