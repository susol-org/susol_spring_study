package com.susol.susolstudy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/study")
public class PostController {

    @GetMapping("/post")
    public String studyPostPage() {
        return "studypost/studypost";
    }


}
