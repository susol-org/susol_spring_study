package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.PostFileRepository;
import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.model.entity.PostFile;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostFileService {
    private final PostFileRepository postFileRepository;
    private final StudyMemberRepository studyMemberRepository;

    public PostFile fileDownload(String userEmailId, String renamedFileName) {
        PostFile postFile = postFileRepository.findByPostRenamedFileName(renamedFileName)
                                .orElseThrow(() -> new EntityNotFoundException("파일이 존재하지 않습니다."));

        int studyId = postFile.getPost().getStudy().getStudyId();
        boolean isMember = studyMemberRepository.existsByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId);
        if(!isMember) throw new AccessDeniedException("접근 권한이없습니다.");

        return postFile;
    }
}
