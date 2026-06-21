package com.susol.susolstudy.dao;

import com.susol.susolstudy.model.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Integer> {
    List<StudyMember> findAllByUser_UserEmailId(String userEmailId);
}
