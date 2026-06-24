package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.StudyNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudyNoteRepositoryCustom {
    Page<StudyNote> searchStudyNotes(String userEmailId, String keyword,
                                     Integer studyId, Pageable pageable);
}
