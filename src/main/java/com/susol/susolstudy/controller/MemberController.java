package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.MemberResponseDTO;
import com.susol.susolstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/studies")
public class MemberController {

    private final MemberService memberService;

    // 멤버 목록 조회 (M-01) - 관리 화면
    @GetMapping("/{studyId}/members")
    public String selectMember(@AuthenticationPrincipal UserDetails user,
                               @PathVariable int studyId,
                               Model model) {
        List<MemberResponseDTO> memberList = memberService.selectMember(user.getUsername(), studyId);
        model.addAttribute("memberList", memberList);
        model.addAttribute("studyId", studyId);
        return "study/members";
    }

    // 멤버 강퇴 (M-02) - 버튼/JS에서 호출
    @DeleteMapping("/{studyId}/members/{memberId}")
    public ResponseEntity<String> deleteMember(@AuthenticationPrincipal UserDetails user,
                                               @PathVariable int studyId,
                                               @PathVariable int memberId) {
        memberService.deleteMember(user.getUsername(), studyId, memberId);
        return ResponseEntity.ok("멤버를 강퇴했습니다.");
    }

    // 리더 위임 (M-03) - 버튼/JS에서 호출
    @PatchMapping("/{studyId}/members/{memberId}/delegate")
    public ResponseEntity<String> delegateLeader(@AuthenticationPrincipal UserDetails user,
                                                 @PathVariable int studyId,
                                                 @PathVariable int memberId) {
        memberService.delegateLeader(user.getUsername(), studyId, memberId);
        return ResponseEntity.ok("리더를 위임했습니다.");
    }

    // 스터디 탈퇴 (M-04) - 버튼/JS에서 호출
    @DeleteMapping("/{studyId}/members/me")
    public ResponseEntity<String> leaveStudy(@AuthenticationPrincipal UserDetails user,
                                             @PathVariable int studyId) {
        memberService.leaveStudy(user.getUsername(), studyId);
        return ResponseEntity.ok("스터디에서 탈퇴했습니다.");
    }
}
