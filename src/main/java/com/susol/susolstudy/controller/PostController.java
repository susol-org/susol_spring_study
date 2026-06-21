package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.MyStudyListResponseDTO;
import com.susol.susolstudy.model.dto.PostDetailResponseDTO;
import com.susol.susolstudy.model.dto.PostResponseDTO;
import com.susol.susolstudy.model.entity.Post;
import com.susol.susolstudy.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/study")
public class PostController {

    private final PostService service;

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
        model.addAttribute("postDetail", postDetail);
        return "studypost/postdetail";
    }

}
