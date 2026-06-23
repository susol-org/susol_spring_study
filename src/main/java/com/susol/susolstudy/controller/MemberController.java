package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.MemberResponseDTO;
import com.susol.susolstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{studyId}/members")
    public List<MemberResponseDTO> selectMember(@AuthenticationPrincipal UserDetails user, @PathVariable int studyId) {
        List<MemberResponseDTO> selectMemberResult = memberService.selectMember(user.getUsername(), studyId);
        return selectMemberResult;
    }

    @DeleteMapping("/{studyId}/members/{memberId}")
    public ResponseEntity<String> deleteMember(@AuthenticationPrincipal UserDetails user, @PathVariable int studyId, @PathVariable int memberId) {
        memberService.deleteMember(user.getUsername(), studyId, memberId);
        return ResponseEntity.ok("스터디에서 강퇴되었습니다.");
    }   //강퇴

    @PatchMapping("/{studyId}/members/{memberId}/delegate")
    public ResponseEntity<String>  delegateLeader(@AuthenticationPrincipal UserDetails user, @PathVariable int studyId, @PathVariable int memberId) {
        memberService.delegateLeader(user.getUsername(), studyId, memberId);
        return ResponseEntity.ok("직책이 변경되었습니다..");
    }

    @DeleteMapping("/{studyId}/members/me")
    public ResponseEntity<String>leaveStudy(@AuthenticationPrincipal UserDetails user, @PathVariable int studyId) {
        memberService.leaveStudy(user.getUsername(), studyId);
        return ResponseEntity.ok("스터디에서 탈퇴하였습니다.");
    }  //스터디 나가기(탈퇴)
}


