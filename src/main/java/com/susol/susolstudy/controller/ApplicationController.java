package com.susol.susolstudy.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationController {

   @PostMapping("/application")
    public void createUserStudyApplication() {}   //지원

    @DeleteMapping("/application/{id}")
    public void deleteStudyApplication() {}       // 거절

    @GetMapping("/application/{id}")
    public List<Object> selectMemberApplication() {return null;}    // 조회

    @DeleteMapping("/application/{id}/cancel")
    public void cancelStudyApplication() {}    //취소

    @PatchMapping("/application/{id}/approve")
    public void approveStudyApplication() {}    //승인






}
