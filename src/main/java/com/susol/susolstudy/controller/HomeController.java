package com.susol.susolstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/main")
public class HomeController {

    @GetMapping("/home")
    public String mainPage() {
        return "index";
    }
}
