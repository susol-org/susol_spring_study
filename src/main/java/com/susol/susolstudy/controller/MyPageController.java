package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.UserResponseDTO;
import com.susol.susolstudy.model.entity.User;
import com.susol.susolstudy.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService service;

    @GetMapping
    public String MyPage(@AuthenticationPrincipal UserDetails user, Model model) {
        UserResponseDTO searchUser = service.getUserInfo(user.getUsername());
        model.addAttribute("userInfo", searchUser);
        return "mypage/mypage";
    }
}
