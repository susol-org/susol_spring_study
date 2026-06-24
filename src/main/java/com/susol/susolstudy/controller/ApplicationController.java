package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.ApplicationResponseDTO;
import com.susol.susolstudy.service.ApplicationService;
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
public class ApplicationController {

    private final ApplicationService applicationService;

    // 지원자 목록 조회 (A-03) - 리더용 화면
    @GetMapping("/{studyId}/applications")
    public String selectMemberApplication(@AuthenticationPrincipal UserDetails user,
                                          @PathVariable int studyId,
                                          Model model) {
        List<ApplicationResponseDTO> applicationList = applicationService.selectMemberApplication(user.getUsername(), studyId);
        model.addAttribute("applicationList", applicationList);
        model.addAttribute("studyId", studyId);
        return "study/applications";
    }

    // 스터디 지원 (A-01) - 폼 제출
    @PostMapping("/{studyId}/applications")
    public String createUserStudyApplication(@AuthenticationPrincipal UserDetails user,
                                             @PathVariable int studyId,
                                             @RequestParam String message) {
        applicationService.createUserStudyApplication(user.getUsername(), message, studyId);
        return "redirect:/studies/" + studyId;
    }

    // 지원 취소 (A-02) - 버튼/JS에서 호출
    @DeleteMapping("/{studyId}/applications/cancel")
    public ResponseEntity<String> cancelStudyApplication(@AuthenticationPrincipal UserDetails user,
                                                         @PathVariable int studyId) {
        applicationService.cancelStudyApplication(user.getUsername(), studyId);
        return ResponseEntity.ok("지원이 취소되었습니다.");
    }

    // 지원 승인 (A-04) - 버튼/JS에서 호출
    @PatchMapping("/{studyId}/applications/{applicationId}/approve")
    public ResponseEntity<String> approveStudyApplication(@AuthenticationPrincipal UserDetails user,
                                                          @PathVariable int studyId,
                                                          @PathVariable int applicationId) {
        applicationService.approveStudyApplication(user.getUsername(), studyId, applicationId);
        return ResponseEntity.ok("지원이 승인되었습니다.");
    }

    // 지원 거절 (A-05) - 버튼/JS에서 호출
    @PatchMapping("/{studyId}/applications/{applicationId}/reject")
    public ResponseEntity<String> deleteStudyApplication(@AuthenticationPrincipal UserDetails user,
                                                         @PathVariable int studyId,
                                                         @PathVariable int applicationId) {
        applicationService.deleteStudyApplication(user.getUsername(), studyId, applicationId);
        return ResponseEntity.ok("지원이 거절되었습니다.");
    }
}
