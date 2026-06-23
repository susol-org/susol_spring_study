package com.susol.susolstudy.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class StudyNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studyNoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(nullable = false)
    private String studyNoteTitle;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String studyNoteContent;

    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'PRIVATE'")
    private NotePermission studyNoteVisibility;

    private boolean studyNoteIsDelete;

    @CreationTimestamp
    private LocalDateTime studyNoteCreatedAt;

    @CreationTimestamp
    private LocalDateTime studyNoteUpdatedAt;
}
