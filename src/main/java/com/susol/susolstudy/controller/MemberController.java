package com.susol.susolstudy.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/member")
    public void selectMember() {}

    @DeleteMapping("/member/{id}")
    public void deleteMember() {}   //강퇴

    @PatchMapping("/member/{id}/delegate")
    public void delegateLeader() {}

    @DeleteMapping("/member/{id}/leave/role")
    public void leaveStudy() {}  //스터디 나가기
}


