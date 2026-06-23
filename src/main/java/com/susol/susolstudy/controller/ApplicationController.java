package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.ApplicationResponseDTO;
import com.susol.susolstudy.model.entity.StudyApplication;
import com.susol.susolstudy.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/studies")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/{studyId}/applications")
    public ResponseEntity<String> createUserStudyApplication(@AuthenticationPrincipal UserDetails user, @PathVariable int studyId, @RequestBody String message) {
        applicationService.createUserStudyApplication(user.getUsername(), message, studyId);
        return ResponseEntity.ok("지원이 완료되었습니다.");
    }   // 지원

    @DeleteMapping("/{studyId}/applications/cancel")
    public ResponseEntity<String> cancelStudyApplication(@AuthenticationPrincipal UserDetails user, @PathVariable int studyId) {
        applicationService.cancelStudyApplication(user.getUsername(), studyId);
        return ResponseEntity.ok("지원이 취소되었습니다.");
    }    // 취소

    @GetMapping("/{studyId}/applications")
    public List<ApplicationResponseDTO> selectMemberApplication(@AuthenticationPrincipal UserDetails user, @PathVariable int studyId) {
        List<ApplicationResponseDTO> result = applicationService.selectMemberApplication(user.getUsername(), studyId);
        return result;
    }    // 지원자 목록 조회

    @PatchMapping("/{studyId}/applications/{applicationId}/approve")
    public void approveStudyApplication(@PathVariable int studyId, @PathVariable int applicationId) {
    }    // 승인


    // cancel, /approve, /reject 이런 건 행위를 표현하는 단어를 개발자가 직접 정함.
    @PatchMapping("/{studyId}/applications/{applicationId}/reject")
    public void deleteStudyApplication(@PathVariable int studyId, @PathVariable int applicationId) {
    }    // 거절

// - /studies — "스터디들" 이라는 자원 컬렉션. 엔티티 이름이 아니라 REST에서 자원을 표현하는 단어로 복수형으로 쓴다.
// - /{studyId} — 그 중 특정 스터디 하나. 실제 숫자가 들어온다. 예: /studies/1
// - /applications — 그 스터디에 속한 지원서들
// - /cancel — 취소라는 행위


}
