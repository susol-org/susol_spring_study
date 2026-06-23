package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.StudyNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyNoteRepository extends JpaRepository<StudyNote, Integer> {

    Page<StudyNote> findAllByUser_UserEmailId(String userEmailId, Pageable pageable);

    Optional<StudyNote> findByStudyNoteIdAndUser_UserEmailId(int studyNoteId, String username);
}
