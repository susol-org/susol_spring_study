package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.*;
import com.susol.susolstudy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/study")
public class PostController {

    private final PostService service;

    //post가기 이전 페이지
    @GetMapping("/post")
    public String studyPostPage(@AuthenticationPrincipal UserDetails user, Model model) {
        List<MyStudyListResponseDTO> responseDTOList = service.getMyStudyList(user.getUsername());
        model.addAttribute("studyList", responseDTOList);
        return "studypost/studypost";
    }

    // 게시물 리스트
    @GetMapping("/{studyId}/post")
    public String getPostByStudyId(@PathVariable int studyId,
                                                    @AuthenticationPrincipal UserDetails user,
                                                  Model model) {
        List<PostResponseDTO> posts = service.getPostList(studyId, user.getUsername());
        model.addAttribute("postList", posts);
        model.addAttribute("studyId", studyId);
        return "studypost/studypostlist";
    }

    //게시물 상세
    @GetMapping("/{studyId}/post/{postId}")
    public String postDetail(@AuthenticationPrincipal UserDetails user,
                             @PathVariable int postId, @PathVariable int studyId,
                             Model model) {
        PostDetailResponseDTO postDetail = service.getPostByPostId(user.getUsername(), studyId, postId);
        boolean updateAuth = service.checkMyPost(user.getUsername(), postId);

        model.addAttribute("postDetail", postDetail);
        model.addAttribute("updateAuth", updateAuth);

        return "studypost/postdetail";
    }

    //post작성 페이지 이동
    @GetMapping("/{studyId}/post/write")
    public String postWritePage(@PathVariable int studyId, Model model) {
        model.addAttribute("studyId", studyId);
        return "studypost/studypostwrite";
    }

    //post작성
    @PostMapping("/{studyId}/post")
    public String writePost(@PathVariable int studyId,
                            @AuthenticationPrincipal UserDetails user,
                            @ModelAttribute PostWriteRequestDTO postWriteDTO) {
        service.writePost(studyId, user.getUsername(), postWriteDTO);
        return "redirect:/study/" + studyId + "/post";
    }

    //post 수정페이지 이동
    @GetMapping("/{studyId}/post/{postId}/update")
    public String postUpdatePage(@PathVariable int studyId, @PathVariable int postId,
                                 @AuthenticationPrincipal UserDetails user, Model model) {
        PostUpdateResponseDTO updatingPost = service.getUpdatingPost(postId, user.getUsername());

        model.addAttribute("updatingPost", updatingPost);
        model.addAttribute("studyId", studyId);
        model.addAttribute("postId", postId);

        return "studypost/studypostupdate";
    }

    //post update처리
    @PostMapping("/{studyId}/post/{postId}/update")
    public String postUpdate(@PathVariable int studyId, @PathVariable int postId,
                             @AuthenticationPrincipal UserDetails user,
                             @ModelAttribute PostUpdateRequestDTO updateDTO, Model model) {
        service.updatePost(postId, user.getUsername(), updateDTO);

        model.addAttribute("studyId", studyId);
        model.addAttribute("postId", postId);

        return "redirect:/study/" + studyId + "/post/" + postId;
    }

    //postDelete처리
    @DeleteMapping("/{studyId}/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int studyId, @PathVariable int postId,
                                             @AuthenticationPrincipal UserDetails user) {
        service.deletePost(postId, user.getUsername());

        return ResponseEntity.ok("게시물이 삭제되었습니다.");
    }

}
