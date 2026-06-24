package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.StudyNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudyNoteRepository extends JpaRepository<StudyNote, Integer>, StudyNoteRepositoryCustom {

    Page<StudyNote> findAllByUser_UserEmailId(String userEmailId, Pageable pageable);

    Optional<StudyNote> findByStudyNoteIdAndUser_UserEmailId(int studyNoteId, String username);

    boolean existsByStudyNoteIdAndUser_UserEmailId(int studyNoteId, String userEmailId);

    @Query(
        value = """
            SELECT sn FROM StudyNote sn
            JOIN FETCH sn.user
            JOIN FETCH sn.study
            WHERE sn.studyNoteVisibility = 'MEMBER'
            AND sn.studyNoteIsDelete = false
            AND sn.user.userEmailId != :userEmailId
            AND sn.study.studyId IN (
                SELECT sm.study.studyId FROM StudyMember sm
                WHERE sm.user.userEmailId = :userEmailId
            )
        """,
        countQuery = """
            SELECT COUNT(sn) FROM StudyNote sn
            WHERE sn.studyNoteVisibility = 'MEMBER'
            AND sn.studyNoteIsDelete = false
            AND sn.user.userEmailId != :userEmailId
            AND sn.study.studyId IN (
                SELECT sm.study.studyId FROM StudyMember sm
                WHERE sm.user.userEmailId = :userEmailId
            )
        """
    )
    Page<StudyNote> findMemberStudyNotes(@Param("userEmailId") String userEmailId, Pageable pageable);
}