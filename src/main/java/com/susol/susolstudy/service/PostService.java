package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.model.dto.MyStudyListResponseDTO;
import com.susol.susolstudy.model.entity.StudyMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final StudyMemberRepository studyMemberRepository;

    public List<MyStudyListResponseDTO> getMyStudyList(String userEmailId) {
        List<StudyMember> studyList = studyMemberRepository.findAllByUser_UserEmailId(userEmailId);

        return studyList.stream()
                        .map(MyStudyListResponseDTO::entityOf)
                        .toList();
    }
}
