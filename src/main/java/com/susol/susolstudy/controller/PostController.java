package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.MyStudyListResponseDTO;
import com.susol.susolstudy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/study")
public class PostController {

    private final PostService service;

    @GetMapping("/post")
    public String studyPostPage() {
        return "studypost/studypost";
    }

    //내 스터디 가져오기
    @GetMapping("/list")
    public List<MyStudyListResponseDTO> getMyStudyList(@AuthenticationPrincipal UserDetails user) {
        return service.getMyStudyList(user.getUsername());
    }


}
