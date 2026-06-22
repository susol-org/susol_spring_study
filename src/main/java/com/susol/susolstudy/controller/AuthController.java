package com.susol.susolstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "signin";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return null;
    }

    @GetMapping("/findId")
    public String findIdPage() {
        return null;
    }

    @GetMapping("/findPassword")
    public String findPasswordPage() {
        return null;
    }


}
