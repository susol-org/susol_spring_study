package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.StudyApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyApplicationRepository extends JpaRepository<StudyApplication, Integer> {
    boolean existsByStudy_StudyIdAndUser_userEmailId(int StudyId, String userEmailId);
    Optional<StudyApplication> findByStudy_StudyIdAndUser_userEmailId(int studyId, String userEmailId);
    List<StudyApplication> findAllByStudy_StudyId(int studyId); // 특정 스터디에 속한 지원서 전체
}
