package com.susol.susolstudy.controller;

import com.susol.susolstudy.dao.StudyMemberRepository;
import com.susol.susolstudy.model.dto.StudyDetailResponseDTO;
import com.susol.susolstudy.model.dto.StudyRequestDTO;
import com.susol.susolstudy.model.dto.StudyResponseDTO;
import com.susol.susolstudy.model.entity.Role;
import com.susol.susolstudy.model.entity.StudyMember;
import com.susol.susolstudy.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/studies")
public class StudyController {

    private final StudyService studyService;
    private final StudyMemberRepository studyMemberRepository;

    // 스터디 목록 (S-04)
    @GetMapping
    public String selectStudy(Model model) {
        List<StudyResponseDTO> studyList = studyService.selectStudy();
        model.addAttribute("studyList", studyList);
        return "study/studylist";
    }

    // 스터디 개설 폼 페이지
    @GetMapping("/new")
    public String createStudyForm() {
        return "study/studyform";
    }

    // 스터디 상세 (S-05)
    @GetMapping("/{studyId}")
    public String getStudyDetail(@AuthenticationPrincipal UserDetails user,
                                 @PathVariable int studyId, Model model) {
        StudyDetailResponseDTO study = studyService.getStudyDetail(studyId);
        model.addAttribute("study", study);

        // 화면 버튼 노출용 - 현재 사용자의 스터디 내 위치 판단 (백엔드 로직은 그대로)
        boolean loggedIn = (user != null);
        boolean isMember = false;
        boolean isLeader = false;
        if (loggedIn) {
            Optional<StudyMember> found =
                    studyMemberRepository.findByStudy_StudyIdAndUser_UserEmailId(studyId, user.getUsername());
            if (found.isPresent()) {
                isMember = true;
                isLeader = found.get().getRole() == Role.LEADER;
            }
        }
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("isMember", isMember);
        model.addAttribute("isLeader", isLeader);

        return "study/studydetail";
    }

    // 스터디 수정 폼 페이지
    @GetMapping("/{studyId}/update")
    public String updateStudyForm(@PathVariable int studyId, Model model) {
        StudyDetailResponseDTO study = studyService.getStudyDetail(studyId);
        model.addAttribute("study", study);
        return "study/studyupdateform";
    }

    // 스터디 개설 처리 (S-01)
    @PostMapping
    public String createStudy(@AuthenticationPrincipal UserDetails user,
                              @ModelAttribute StudyRequestDTO request) {
        studyService.createStudy(user.getUsername(), request);
        return "redirect:/studies";
    }

    // 스터디 수정 처리 (S-02)
    @PostMapping("/{studyId}/update")
    public String updateStudy(@AuthenticationPrincipal UserDetails user,
                              @PathVariable int studyId,
                              @ModelAttribute StudyRequestDTO request) {
        studyService.updateStudy(user.getUsername(), studyId, request);
        return "redirect:/studies/" + studyId;
    }

    // 스터디 삭제 (S-03) - 버튼/JS에서 호출
    @DeleteMapping("/{studyId}")
    public ResponseEntity<String> deleteStudy(@AuthenticationPrincipal UserDetails user,
                                              @PathVariable int studyId) {
        studyService.deleteStudy(user.getUsername(), studyId);
        return ResponseEntity.ok("스터디가 삭제되었습니다.");
    }

    // 모집 마감 (S-06) - 버튼/JS에서 호출
    @PatchMapping("/{studyId}/close")
    public ResponseEntity<String> closeStudy(@AuthenticationPrincipal UserDetails user,
                                             @PathVariable int studyId) {
        studyService.closeStudy(user.getUsername(), studyId);
        return ResponseEntity.ok("모집이 마감되었습니다.");
    }
}
