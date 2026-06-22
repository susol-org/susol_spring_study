package com.susol.susolstudy.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "post_read_log")
public class PostReadLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postReadLogId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime postReadLogDate;

    public static PostReadLog create(Post post, User user) {
        PostReadLog postReadLog = new PostReadLog();
        postReadLog.post = post;
        postReadLog.user = user;

        return postReadLog;
    }
}
