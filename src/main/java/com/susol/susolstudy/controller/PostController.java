package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.MyStudyListResponseDTO;
import com.susol.susolstudy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


}
