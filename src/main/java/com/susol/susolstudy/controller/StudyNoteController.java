package com.susol.susolstudy.controller;

import com.susol.susolstudy.model.dto.StudyNoteResponseDTO;
import com.susol.susolstudy.service.StudyNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class StudyNoteController {

    private final StudyNoteService service;

    @GetMapping
    public String studyNotePage(@AuthenticationPrincipal UserDetails user, Model model) {
        List<StudyNoteResponseDTO> allStudyNote = service.getAllStudyNote(user.getUsername());
        model.addAttribute("studyNotes", allStudyNote);
        return "studynote/studynote";
    }
}
