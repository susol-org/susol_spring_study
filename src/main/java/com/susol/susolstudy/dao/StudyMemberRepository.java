package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Integer> {
    List<StudyMember> findAllByUser_UserEmailId(String userEmailId); //내 스터디 가져오기

    //해당 게시물을 조회할 권한이 있는지에대한 판단
    boolean existsByStudy_StudyIdAndUser_UserEmailId(int studyId, String userEmailId);
}
