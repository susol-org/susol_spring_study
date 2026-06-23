package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.StudyNoteResponseDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        Pageable pageable = PageRequest.of(page, size, Sort.by("studyNoteCreatedAt").descending());
        Page<StudyNoteResponseDTO> studyNotes = service.getAllStudyNote(user.getUsername(), pageable);

        model.addAttribute("studyNotes", studyNotes.getContent());
        model.addAttribute("totalPages", studyNotes.getTotalPages());
        model.addAttribute("currentPage", page);

        return "studynote/studynote";
//        List<StudyNoteResponseDTO> allStudyNote = service.getAllStudyNote(user.getUsername());
//        model.addAttribute("studyNotes", allStudyNote);
    }
}
