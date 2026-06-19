package com.susol.susolstudy.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

//    private Study study;

//    private User user;

    private PostType postType;

    private String postTitle;

    private String postContent;

    private boolean postDelete;

    private LocalDateTime postCreatedAt;

    private LocalDateTime postUpdatedAt;
}
