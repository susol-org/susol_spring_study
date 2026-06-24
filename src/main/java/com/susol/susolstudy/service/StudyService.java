package com.susol.susolstudy.service;


import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.dao.StudyRepository;
import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.StudyDetailResponseDTO;
import com.susol.susolstudy.model.dto.StudyRequestDTO;
import com.susol.susolstudy.model.dto.StudyResponseDTO;
import com.susol.susolstudy.model.entity.RecruitStatus;
import com.susol.susolstudy.model.entity.Role;
import com.susol.susolstudy.model.entity.Study;
import com.susol.susolstudy.model.entity.StudyMember;
import com.susol.susolstudy.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Transactional
    public void createStudy(String userEmailId, StudyRequestDTO studyRequest){
        User user = userRepository.findByUserEmailId(userEmailId)
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        Study study = Study.builder()
                .studyTitle(studyRequest.getStudyTitle())
                .studyDescription(studyRequest.getStudyDescription())
                .maxMemberCount(studyRequest.getMaxMemberCount())
                .currentMemberCount(1)
                .recruitStatus(RecruitStatus.RECRUIT)
                .studyEndDate(studyRequest.getStudyEndDate())
                .studyCreatedAt(LocalDateTime.now())
                .build();
        studyRepository.save(study);

        StudyMember leader = StudyMember.builder()
                .study(study)
                .user(user)
                .role(Role.LEADER)
                .build();
        studyMemberRepository.save(leader);
    }

    @Transactional
    public void deleteStudy(String userEmailId, int studyId){
        getLeader(studyId, userEmailId);

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new RuntimeException("스터디가 존재하지 않습니다."));
        study.softDelete();
    }

    @Transactional
    public void updateStudy(String userEmailId, int studyId, StudyRequestDTO studyRequest) {
        getLeader(studyId, userEmailId);

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new RuntimeException("스터디가 존재하지 않습니다."));

        study.update(
                studyRequest.getStudyTitle(),
                studyRequest.getStudyDescription(),
                studyRequest.getMaxMemberCount(),
                studyRequest.getStudyEndDate()
        );
    }

    public List<StudyResponseDTO> selectStudy() {
        List<Study> studyList = studyRepository.findAllByDeletedAtIsNull();

        return studyList.stream()
                .map(StudyResponseDTO::entityOf)
                .toList();
    }

    public StudyDetailResponseDTO getStudyDetail(int studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new RuntimeException("스터디가 존재하지 않습니다."));

        List<StudyMember> memberList = studyMemberRepository.findAllByStudy_StudyId(studyId);

        return StudyDetailResponseDTO.entityOf(study, memberList);
    }

    @Transactional
    public void closeStudy(String userEmailId, int studyId) {
        getLeader(studyId, userEmailId);

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new RuntimeException("스터디가 존재하지 않습니다."));
        study.closeRecruit();
    }

    private StudyMember getLeader(int studyId, String userEmailId) {
        StudyMember member = studyMemberRepository.findByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId)
                .orElseThrow(() -> new AccessDeniedException("접근 권한이 없습니다."));
        if(member.getRole() != Role.LEADER) {
            throw new AccessDeniedException("리더만 가능합니다.");
        }
        return member;
    }
}


