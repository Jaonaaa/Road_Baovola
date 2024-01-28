package com.project.s5.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.s5.Utils.Status;
import com.project.s5.models.Employer;
import com.project.s5.models.Grade;
import com.project.s5.models.Materiaux;
import com.project.s5.service.EmployerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @GetMapping
    private Status getEmps() {
        try {
            List<Employer> emps = employerService.getAllEmployerWithGrader();
            return Status.builder().status("ok").data(emps).build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @GetMapping("/grades")
    private Status getGrades() {
        try {
            List<Grade> grades = employerService.getAllGrade();
            return Status.builder().status("ok").data(grades).build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }
    // POST

    @PostMapping
    public Status addEmp(@RequestBody Employer employer) {
        try {
            employerService.addEmployer(employer);
            return Status.builder().status("ok").details("Employe inserted").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

    @PostMapping("/grade")
    public Status addGrade(@RequestBody Grade grade) {
        try {
            employerService.addGrade(grade);
            return Status.builder().status("ok").details("Grade inserted").build();
        } catch (Exception e) {
            return Status.builder().details(e.getMessage()).status("error").build();
        }
    }

}
