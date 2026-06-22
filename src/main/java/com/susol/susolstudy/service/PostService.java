package com.susol.susolstudy.service;

import com.susol.susolstudy.dao.PostReadLogRepository;
import com.susol.susolstudy.dao.PostRepository;
import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.dao.UserRepository;
import com.susol.susolstudy.model.dto.*;
import com.susol.susolstudy.model.entity.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final StudyMemberRepository studyMemberRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostReadLogRepository postReadLogRepository;

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

    @Transactional
    public PostDetailResponseDTO getPostByPostId(String userEmailId, int studyId, int postId) {
//        boolean isMember = studyMemberRepository.existsByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId);
//
//        if(!isMember) throw new AccessDeniedException("접근 권한이 없습니다.");
        StudyMember studyMember = studyMemberRepository.findByStudy_StudyIdAndUser_UserEmailId(studyId, userEmailId)
                .orElseThrow(() -> new AccessDeniedException("접근 권한이 없습니다."));

        Post post = postRepository.findByPostIdAndStudy_StudyId(postId, studyId)
                            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));

        User user = studyMember.getUser();

        boolean isReadToday = postReadLogRepository.existsReadLog(user, post, LocalDate.now());
        boolean isMine = checkMyPost(userEmailId, postId);

        System.out.println(post);
        System.out.println(user);
        if(!isReadToday && !isMine) {
            System.out.println("isReadToday : " + isReadToday + " isMine : " + isMine);
            PostReadLog postReadLog = PostReadLog.create(post, user);
            postReadLogRepository.save(postReadLog);
            post.setPostViewCount(post.getPostViewCount() + 1);
        }

        return PostDetailResponseDTO.entityOf(post);
    }

    public boolean checkMyPost(String username, int postId) {
        return postRepository.existsByPostIdAndUser_UserEmailId(postId, username);
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

    public PostUpdateResponseDTO getUpdatingPost(int postId, String userEmailId) {
        Post post = postRepository.findByPostIdAndUser_UserEmailId(postId, userEmailId)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));

        return PostUpdateResponseDTO.entityOf(post);
    }

    @Transactional
    public void updatePost(int postId, String userEmailId, PostUpdateRequestDTO updatePost) {
        Post post = postRepository.findByPostIdAndUser_UserEmailId(postId, userEmailId)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));

        post.setPostType(updatePost.getPostType());
        post.setPostTitle(updatePost.getPostTitle());
        post.setPostContent(updatePost.getPostContent());
        post.setPostUpdatedAt(LocalDateTime.now());
    }

    public void deletePost(int postId, String userEmailId) {
        Post post = postRepository.findByPostIdAndUser_UserEmailId(postId, userEmailId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));

        postRepository.delete(post);
    }
}
