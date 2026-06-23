package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.FindIdRequestDTO;
import com.susol.susolstudy.model.dto.SignUpRequestDTO;
import com.susol.susolstudy.model.dto.UserResponseDTO;
import com.susol.susolstudy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    //로그인 페이지 이동
    @GetMapping("/login")
    public String loginPage() {
        return "signin";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "auth/signup";
    }

    @GetMapping("/id/duplicate")
    public ResponseEntity<Map<String, Boolean>> duplicateCheckEmailId(@RequestParam String userEmailId) {
        boolean checkResult = service.duplicateCheckEmailId(userEmailId);
        return ResponseEntity.ok(Map.of("checkResult", checkResult));
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute SignUpRequestDTO signUpDTO, RedirectAttributes redirectAttributes) {
        service.signUp(signUpDTO);
        redirectAttributes.addAttribute("successMessage", "회원가입이 완료되었습니다.");
        return "redirect:/auth/login";
    }

    @GetMapping("/findId")
    public String findIdPage() {
        return "auth/findid";
    }

    @GetMapping("/user/findid")
    public ResponseEntity<?> findId(@ModelAttribute FindIdRequestDTO findIdRequestDTO) {
        String emailId = service.findId(findIdRequestDTO);

        return emailId == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(emailId);
    }

    @GetMapping("/findPassword")
    public String findPasswordPage() {
        return null;
    }


}
