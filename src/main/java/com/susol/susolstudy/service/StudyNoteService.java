package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.dao.StudyNoteRepository;
import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.StudyNoteResponseDTO;
import com.susol.susolstudy.model.entity.StudyNote;
import lombok.RequiredArgsConstructor;
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
    public List<StudyNoteResponseDTO> getAllStudyNote(String userEmailId) {
        List<StudyNote> studyNoteList = studyNoteRepository.findAllByUser_UserEmailId(userEmailId);

        return studyNoteList.stream()
                            .map(StudyNoteResponseDTO::entityOf)
                            .toList();
    }
}
