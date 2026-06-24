package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.*;
import com.susol.susolstudy.service.StudyNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class StudyNoteController {

    private final StudyNoteService service;

    @GetMapping
    public String studyNotePage(@AuthenticationPrincipal UserDetails user,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                                                    Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("studyNoteId").descending());
        Page<StudyNoteResponseDTO> studyNotes = service.getAllStudyNote(user.getUsername(), pageable);

        model.addAttribute("studyNotes", studyNotes.getContent());
        model.addAttribute("totalPages", studyNotes.getTotalPages());
        model.addAttribute("currentPage", page);

        return "studynote/studynote";
//        List<StudyNoteResponseDTO> allStudyNote = service.getAllStudyNote(user.getUsername());
//        model.addAttribute("studyNotes", allStudyNote);
    }

    @GetMapping("/{studyNoteId}")
    public String studyNoteDetail(@AuthenticationPrincipal UserDetails user,
                                                    @PathVariable int studyNoteId, Model model) {
        StudyNoteDetailResponseDTO studyNote = service.studyNoteDetail(user.getUsername(), studyNoteId);
        model.addAttribute("studyNote", studyNote);
        return "studynote/studynotedetail";
    }

    @GetMapping("/write")
    public String studyNoteWritePage(@AuthenticationPrincipal UserDetails user, Model model) {
        List<JoinStudyResponseDTO> joinStudies = service.getJoinStudy(user.getUsername());
        model.addAttribute("joinStudy", joinStudies);
        return "studynote/studynotewrite";
    }

    @PostMapping("/write")
    public String studyNoteWrite(@AuthenticationPrincipal UserDetails user,
                                 @ModelAttribute StudyNoteWriteRequestDTO studyNoteWriteDTO) {
        service.studyNoteWrite(user.getUsername(), studyNoteWriteDTO);
        return "redirect:/note";
    }

    // other == 다른사람 스터디 노트
//    @GetMapping("/other")
//    public String otherStudyNotePage(@AuthenticationPrincipal UserDetails user, Model model) {
//
//    }
}
