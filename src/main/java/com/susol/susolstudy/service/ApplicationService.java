package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.StudyApplicationRepository;
import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.dao.StudyRepository;
import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.ApplicationResponseDTO;
import com.susol.susolstudy.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final StudyApplicationRepository studyApplicatiobRepoitory;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;

    public void createUserStudyApplication(String userEmailId, String message, int studyId) {
        if (studyApplicatiobRepoitory.existsByStudy_StudyIdAndUser_userEmailId(studyId, userEmailId)) {
            throw new RuntimeException("이미 지원했습니다.");
        }
        User user = userRepository.findByUserEmailId(userEmailId)
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new RuntimeException("스터디가 존재하지 않습니다."));

        StudyApplication application = StudyApplication.builder()
                .study(study)
                .user(user)
                .message(message)
                .status(ApplicationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        studyApplicatiobRepoitory.save(application);
    }

    public void cancelStudyApplication(String userEmailId, int studyId) {
        StudyApplication application = studyApplicatiobRepoitory.findByStudy_StudyIdAndUser_userEmailId(studyId, userEmailId)
                .orElseThrow(() -> new RuntimeException("지원 내역이 없습니다."));

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new RuntimeException(("이미 처리된 지원서입니다."));
        }
        studyApplicatiobRepoitory.delete(application);
    }

    @Transactional
    public void deleteStudyApplication(String userEmailId, int studyId, int applicationId) {
        StudyMember member = studyMemberRepository.findByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId)
                .orElseThrow(() -> new AccessDeniedException("접근 권한이 없습니다."));
        if(member.getRole() != Role.LEADER) {
            throw new AccessDeniedException("리더만 가능합니다.");
        }

        StudyApplication application = studyApplicatiobRepoitory.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("지원 내역이 없습니다."));

        application.updateStatus(ApplicationStatus.REJECTED);
    }

    public List<ApplicationResponseDTO> selectMemberApplication(String userEmailId, int studyId) {
        boolean isMember = studyMemberRepository.existsByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId);
        if (!isMember) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        List<StudyApplication> applicationsList = studyApplicatiobRepoitory.findAllByStudy_StudyId(studyId);
        return applicationsList.stream().map(ApplicationResponseDTO::entityOf).toList();
    }

    @Transactional
    public void approveStudyApplication(String userEmailId, int studyId, int applicationId) {
        StudyMember member = studyMemberRepository.findByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId)
                .orElseThrow(() -> new AccessDeniedException("접근 권한이 없습니다."));

        if (member.getRole() != Role.LEADER) {
            throw new AccessDeniedException("리더만 가능합니다.");
        }

        StudyApplication application = studyApplicatiobRepoitory.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("지원 내역이 없습니다."));

        application.updateStatus(ApplicationStatus.APPROVED);

        StudyMember newMember = StudyMember.builder()
                .study(application.getStudy())
                .user(application.getUser())
                .role(Role.MEMBER)
                .build();
        studyMemberRepository.save(newMember);
        application.getStudy().increaseMemberCount();

    }
}
