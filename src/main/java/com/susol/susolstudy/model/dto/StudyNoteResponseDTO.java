package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.StudyNote;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class StudyNoteResponseDTO {
    private int studyNoteId;
    private String writerEmailId;
    private String studyTitle;
    private String studyNoteTitle;
    private String studyNoteCreatedAt;

    public static StudyNoteResponseDTO entityOf(StudyNote studyNote) {
        return new StudyNoteResponseDTO(
            studyNote.getStudyNoteId(),
            studyNote.getUser().getUserEmailId(),
            studyNote.getStudy().getStudyTitle(),
            studyNote.getStudyNoteTitle(),
            studyNote.getStudyNoteCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss"))
        );
    }
}
