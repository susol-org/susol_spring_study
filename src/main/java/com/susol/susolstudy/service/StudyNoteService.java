package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.dao.StudyNoteRepository;
import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.StudyNoteDetailResponseDTO;
import com.susol.susolstudy.model.dto.StudyNoteResponseDTO;
import com.susol.susolstudy.model.entity.StudyNote;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyNoteService {

    private final StudyNoteRepository studyNoteRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Transactional
    public Page<StudyNoteResponseDTO> getAllStudyNote(String userEmailId, Pageable pageable) {
        return studyNoteRepository.findAllByUser_UserEmailId(userEmailId, pageable)
                                                    .map(StudyNoteResponseDTO::entityOf);

//        return studyNoteList.stream()
//                            .map(StudyNoteResponseDTO::entityOf)
//                            .toList();
    }

    @Transactional(readOnly = true)
    public StudyNoteDetailResponseDTO studyNoteDetail(String username, int studyNoteId) {
        StudyNote studyNote = studyNoteRepository.findByStudyNoteIdAndUser_UserEmailId(studyNoteId, username)
                                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));

        return StudyNoteDetailResponseDTO.entityOf(studyNote);

    }
}
