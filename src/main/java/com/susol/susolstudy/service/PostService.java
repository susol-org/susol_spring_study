package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.PostRepository;
import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.MyStudyListResponseDTO;
import com.susol.susolstudy.model.dto.PostDetailResponseDTO;
import com.susol.susolstudy.model.dto.PostResponseDTO;
import com.susol.susolstudy.model.dto.PostWriteRequestDTO;
import com.susol.susolstudy.model.entity.Post;
import com.susol.susolstudy.model.entity.StudyMember;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final StudyMemberRepository studyMemberRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<MyStudyListResponseDTO> getMyStudyList(String userEmailId) {
        List<StudyMember> studyList = studyMemberRepository.findAllByUser_UserEmailId(userEmailId);

        return studyList.stream()
                        .map(MyStudyListResponseDTO::entityOf)
                        .toList();
    }

    public List<PostResponseDTO> getPostList(int studyId, String userEmailId) {
        boolean validationResult = studyMemberRepository.existsByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId);

        if(!validationResult) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        List<Post> postList = postRepository.findAllByStudy_StudyId(studyId);

        return postList.stream()
                        .map(PostResponseDTO::entityOf)
                        .toList();

    }

    public PostDetailResponseDTO getPostByPostId(String userEmailId, int studyId, int postId) {
        boolean isMember = studyMemberRepository.existsByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId);

        if(!isMember) throw new AccessDeniedException("접근 권한이 없습니다.");

        Post post = postRepository.findByPostIdAndStudy_StudyId(postId, studyId)
                            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));

        return PostDetailResponseDTO.entityOf(post);
    }

    @Transactional
    public void writePost(int studyId, String userEmailId, PostWriteRequestDTO postWriteDTO) {
        //해당 스터디게시판에 작성권한여부 체크
        StudyMember searchStudyMember = studyMemberRepository
                                            .findByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId)
                                            .orElseThrow(() -> new AccessDeniedException("접근 권한이 없습니다."));

        Post writePost = Post.create(searchStudyMember.getStudy(), searchStudyMember.getUser(), postWriteDTO);
        postRepository.save(writePost);
    }
}
