package com.susol.susolstudy.model.dto;

import com.susol.susolstudy.model.entity.NotePermission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyNoteWriteRequestDTO {
    private int studyId;
    private String studyNoteTitle;
    private String studyNoteContent;
    private NotePermission studyNoteVisibility;
}
