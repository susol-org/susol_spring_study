package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.model.dto.MemberResponseDTO;
import com.susol.susolstudy.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final StudyMemberRepository studyMemberRepository;

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> selectMember(String userEmailId, int studyId) {
        boolean isMember = studyMemberRepository.existsByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId);
    if (!isMember) {
        throw new AccessDeniedException("접근 권한이 없습니다.");
    }

        List<StudyMember> studyMemberRepositoryList = studyMemberRepository.findAllByStudy_StudyId(studyId);
        return studyMemberRepositoryList.stream().map(MemberResponseDTO::entityOf).toList();
    }

    @Transactional
    public void deleteMember(String userEmailId, int studyId, int memberId) {
        getLeader(studyId, userEmailId);
        StudyMember target = studyMemberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));

        studyMemberRepository.delete(target);
        target.getStudy().decreaseMemberCount();
    }

    @Transactional
    public void delegateLeader(String userEmailId, int studyId, int memberId) {
        StudyMember currentLeader = getLeader(studyId, userEmailId);
        StudyMember newLeader = studyMemberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));

        currentLeader.updateRole(Role.MEMBER);
        newLeader.updateRole(Role.LEADER);
    }

    @Transactional
    public void leaveStudy(String userEmailId, int studyId) {
        StudyMember member = getMember(studyId, userEmailId);
        if(member.getRole() == Role.LEADER) {
            throw new AccessDeniedException("리더는 탈퇴가 불가능합니다. 권한을 위임해주세요.");
        }

        studyMemberRepository.delete(member);
        member.getStudy().decreaseMemberCount();
    }

    private StudyMember getMember(int studyId, String userEmailId) {
        return studyMemberRepository.findByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));
    }

    private StudyMember getLeader(int studyId, String userEmailId) {
        StudyMember member = getMember(studyId, userEmailId);
        if(member.getRole() != Role.LEADER) {
            throw new AccessDeniedException("리더만 가능합니다.");
        }
        return member;
    }

}
