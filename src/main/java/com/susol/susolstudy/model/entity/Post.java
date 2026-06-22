package com.susol.susolstudy.model.entity;

import com.susol.susolstudy.model.dto.PostWriteRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostFile> postFiles = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private PostType postType;

    private String postTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String postContent;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int postViewCount;

    private boolean postDelete;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime postCreatedAt;

    private LocalDateTime postUpdatedAt;

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setPostUpdatedAt(LocalDateTime postUpdatedAt) {
        this.postUpdatedAt = postUpdatedAt;
    }

    public void setPostViewCount(int postViewCount) {
        this.postViewCount = postViewCount;
    }

    public static Post create(Study study, User user, PostWriteRequestDTO postDTO) {
        Post post = new Post();
        post.study = study;
        post.user = user;
        post.postType = postDTO.getPostType();
        post.postTitle = postDTO.getPostTitle();
        post.postContent = postDTO.getPostContent();

        return post;
    }
}
