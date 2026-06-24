package com.susol.susolstudy.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class StudyApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    private String message;

    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public void updateStatus(ApplicationStatus applicationStatus){
        this.status = applicationStatus;

    }
}
