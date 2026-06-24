package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.NotePermission;
import com.susol.susolstudy.model.entity.StudyNote;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class StudyNoteDetailResponseDTO {
    private int studyNoteId;
    private String studyNoteWriter;
    private String studyName;
    private String studyNoteTitle;
    private String studyNoteContent;
    private NotePermission studyNoteVisibility;
    private String studyNoteCreatedAt;
    private String studyNoteUpdatedAt;

    public static StudyNoteDetailResponseDTO entityOf(StudyNote studyNote) {
        return new StudyNoteDetailResponseDTO(
                studyNote.getStudyNoteId(),
                studyNote.getUser().getUserEmailId(),
                studyNote.getStudy().getStudyTitle(),
                studyNote.getStudyNoteTitle(),
                studyNote.getStudyNoteContent(),
                studyNote.getStudyNoteVisibility(),
                studyNote.getStudyNoteCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss")),
                studyNote.getStudyNoteUpdatedAt() != null ?
                                studyNote.getStudyNoteUpdatedAt()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss")) : null
        );
    }
}
