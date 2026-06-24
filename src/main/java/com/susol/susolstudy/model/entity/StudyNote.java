package com.susol.susolstudy.model.entity;

import com.susol.susolstudy.model.dto.StudyNoteWriteRequestDTO;
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
    @Column(nullable = false)
    private LocalDateTime studyNoteCreatedAt;

    private LocalDateTime studyNoteUpdatedAt;

    public static StudyNote create(User user, Study study, StudyNoteWriteRequestDTO studyNoteWriteDTO) {
        StudyNote studyNote = new StudyNote();
        studyNote.user = user;
        studyNote.study = study;
        studyNote.studyNoteTitle = studyNoteWriteDTO.getStudyNoteTitle();
        studyNote.studyNoteContent = studyNoteWriteDTO.getStudyNoteContent();
        studyNote.studyNoteVisibility = studyNoteWriteDTO.getStudyNoteVisibility();

        return studyNote;
    }
}
