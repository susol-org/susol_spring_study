package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.dao.StudyNoteRepository;
import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.JoinStudyResponseDTO;
import com.susol.susolstudy.model.dto.StudyNoteDetailResponseDTO;
import com.susol.susolstudy.model.dto.StudyNoteResponseDTO;
import com.susol.susolstudy.model.dto.StudyNoteWriteRequestDTO;
import com.susol.susolstudy.model.entity.StudyMember;
import com.susol.susolstudy.model.entity.StudyNote;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyNoteService {

    private final StudyNoteRepository studyNoteRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Transactional(readOnly = true)
    public Page<StudyNoteResponseDTO> getAllStudyNote(String userEmailId, String keyword,
                                                        Integer studyId, Pageable pageable) {
        return studyNoteRepository.searchStudyNotes(userEmailId, keyword, studyId, pageable)
                                                            .map(StudyNoteResponseDTO::entityOf);
    }

    @Transactional(readOnly = true)
    public StudyNoteDetailResponseDTO studyNoteDetail(String username, int studyNoteId) {
        StudyNote studyNote = studyNoteRepository.findByStudyNoteIdAndUser_UserEmailId(studyNoteId, username)
                                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));

        return StudyNoteDetailResponseDTO.entityOf(studyNote);
    }

    public boolean checkMyStudyNote(String userEmailId, int studyNoteId) {
        return studyNoteRepository.existsByStudyNoteIdAndUser_UserEmailId(studyNoteId, userEmailId);
    }

    @Transactional(readOnly = true)
    public List<JoinStudyResponseDTO> getJoinStudy(String userEmailId) {
        List<StudyMember> joinStudy = studyMemberRepository.findAllByUser_UserEmailId(userEmailId);
        if(joinStudy == null || joinStudy.isEmpty()) throw new AccessDeniedException("참여한 스터디가 없습니다.");

        return joinStudy.stream().map(JoinStudyResponseDTO::entityOf).toList();
    }

    @Transactional(readOnly = true)
    public List<JoinStudyResponseDTO> getJoinStudyOrEmpty(String userEmailId) {
        List<StudyMember> joinStudy = studyMemberRepository.findAllByUser_UserEmailId(userEmailId);
        if(joinStudy.isEmpty()) return List.of();

        return joinStudy.stream().map(JoinStudyResponseDTO::entityOf).toList();
    }

    @Transactional
    public void studyNoteWrite(String userEmailId, StudyNoteWriteRequestDTO studyNoteWriteDTO) {
        StudyMember studyMember =
                studyMemberRepository.findByStudy_StudyIdAndUser_UserEmailId(
                        studyNoteWriteDTO.getStudyId(),
                        userEmailId
                ).orElseThrow(() -> new AccessDeniedException("접근 권한이 없습니다."));

        StudyNote studyNote =
                StudyNote.create(studyMember.getUser(), studyMember.getStudy(), studyNoteWriteDTO);

        studyNoteRepository.save(studyNote);
    }

    @Transactional(readOnly = true)
    public Page<StudyNoteResponseDTO> getAllMemberStudyNote(String userEmailId, Pageable pageable) {
        Page<StudyNote> memberStudyNoteList =
                            studyNoteRepository.findMemberStudyNotes(userEmailId, pageable);

        return memberStudyNoteList.map(StudyNoteResponseDTO::entityOf);
    }

    @Transactional
    public void deleteStudyNote(String userEmailId, int studyNoteId) {
        StudyNote studyNote
                = studyNoteRepository.findByStudyNoteIdAndUser_UserEmailId(studyNoteId, userEmailId)
                        .orElseThrow(() -> new EntityNotFoundException("게시물이 없습니다."));

        studyNote.setStudyNoteIsDelete();
    }
}
