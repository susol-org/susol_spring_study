package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.StudyDetailResponseDTO;
import com.susol.susolstudy.model.dto.StudyRequestDTO;
import com.susol.susolstudy.model.dto.StudyResponseDTO;
import com.susol.susolstudy.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/studies")
public class StudyController {

    private final StudyService studyService;

    @GetMapping
    public List<StudyResponseDTO> selectStudy() {
        return studyService.selectStudy();
    }

    @GetMapping("/{studyId}")
    public StudyDetailResponseDTO getStudyDetail(@PathVariable int studyId) {
        return studyService.getStudyDetail(studyId);
    }

    @PostMapping
    public ResponseEntity<String> createStudy(@AuthenticationPrincipal UserDetails user,
                                              @RequestBody StudyRequestDTO request) {
        studyService.createStudy(user.getUsername(), request);
        return ResponseEntity.ok("스터디가 개설되었습니다.");
    }

    @PutMapping("/{studyId}")
    public ResponseEntity<String> updateStudy(@AuthenticationPrincipal UserDetails user,
                                              @PathVariable int studyId,
                                              @RequestBody StudyRequestDTO request) {
        studyService.updateStudy(user.getUsername(), studyId, request);
        return ResponseEntity.ok("스터디 정보가 수정되었습니다.");
    }

    @DeleteMapping("/{studyId}")
    public ResponseEntity<String> deleteStudy(@AuthenticationPrincipal UserDetails user,
                                              @PathVariable int studyId) {
        studyService.deleteStudy(user.getUsername(), studyId);
        return ResponseEntity.ok("스터디가 삭제되었습니다.");
    }

    @PatchMapping("/{studyId}/close")
    public ResponseEntity<String> closeStudy(@AuthenticationPrincipal UserDetails user,
                                             @PathVariable int studyId) {
        studyService.closeStudy(user.getUsername(), studyId);
        return ResponseEntity.ok("모집이 마감되었습니다.");
    }
}
