package com.susol.susolstudy.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudyController {


    @GetMapping("/studies")
    public List<Object> selectStudy() {return null}


    @PostMapping("/studies")
    public void createStudy() {}

    @PutMapping("/studies/{id}")
    public void updateStudy() {}

    @DeleteMapping("/studies/{id}")
    public void deleteStudy() {}

    @GetMapping("/studies/{id}")
    public Object  getStudyDetail() {return null}

    @PatchMapping("/studies/{id}/close")
    public void closeStudy() {}


}
