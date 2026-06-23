package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Integer> {
    List<StudyMember> findAllByUser_UserEmailId(String userEmailId); //내 스터디 가져오기

    //해당 게시물을 조회할 권한이 있는지에대한 판단
    boolean existsByStudy_StudyIdAndUser_UserEmailId(int studyId, String userEmailId);
    //특정 멤버 한 명을 찾음
    Optional<StudyMember> findByStudy_StudyIdAndUser_UserEmailId(int studyId, String userEmailId);
    //특정 스터디에 속한 멤머 전부 찾음
    List<StudyMember> findAllByStudy_StudyId(int studyId);

}
