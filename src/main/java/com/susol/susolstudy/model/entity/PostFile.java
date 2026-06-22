package com.susol.susolstudy.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postFileId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String postOriginalFileName;

    @Column(nullable = false)
    private String postRenamedFileName;

    public static PostFile create(Post post, String postOriginalFileName, String postRenamedFileName) {
        PostFile postFile = new PostFile();
        postFile.post = post;
        postFile.postOriginalFileName = postOriginalFileName;
        postFile.postRenamedFileName = postRenamedFileName;

        return postFile;
    }
}
