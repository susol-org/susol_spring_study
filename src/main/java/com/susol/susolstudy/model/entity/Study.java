package com.susol.susolstudy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studyId;

    private String studyTitle;
    private String studyDescription;
    private int maxMemberCount;
    private int currentMemberCount;

    @Enumerated(value = EnumType.STRING)
    private RecruitStatus recruitStatus;

    private LocalDateTime studyStartDate;
    private LocalDateTime studyEndDate;
    private LocalDateTime studyCreatedAt;
    private LocalDateTime studyUpdatedAt;
    private LocalDateTime deletedAt;


    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void closeRecruit() {
        this.recruitStatus = RecruitStatus.CLOSED;
    }

    public void increaseMemberCount() {
        this.currentMemberCount++;
    }

    public void decreaseMemberCount() {
        this.currentMemberCount--;
    }

    public void update(String studyTitle, String studyDescription, int maxMemberCount, LocalDateTime studyEndDate) {
        this.studyTitle = studyTitle;
        this.studyDescription = studyDescription;
        this.maxMemberCount = maxMemberCount;
        this.studyEndDate = studyEndDate;
        this.studyUpdatedAt = LocalDateTime.now();
    }
}
