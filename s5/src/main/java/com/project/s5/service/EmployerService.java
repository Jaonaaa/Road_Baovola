package com.project.s5.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.project.s5.models.Employer;
import com.project.s5.models.Grade;

import com.project.s5.repository.EmployerRepo;
import com.project.s5.repository.GradeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployerService {

    private final EmployerRepo employerRepo;
    private final GradeRepo gradeRepo;

    // GET
    public List<Employer> getAllEmployerWithGrader() {
        List<Employer> employers = employerRepo.findAll();
        for (Employer employer : employers) {
            long yearsDifference = getYearsBetween(employer.getDate_embauche(),
                    new Timestamp(System.currentTimeMillis()));
            List<Grade> grades = getEligibleGrade(yearsDifference);
            if (grades.size() > 0) {
                Collections.sort(grades, Comparator.comparingDouble(Grade::getUpgrade_after).reversed());
                employer.setGrade(grades.get(0));
            }
        }
        return employers;
    }

    public long getYearsBetween(Timestamp one, Timestamp two) {
        LocalDateTime localDateTime1 = one.toLocalDateTime();
        LocalDateTime localDateTime2 = two.toLocalDateTime();
        return ChronoUnit.YEARS.between(localDateTime1, localDateTime2);
    }

    public List<Grade> getEligibleGrade(long years) {
        List<Grade> grades = gradeRepo.findAll();
        List<Grade> grades_valid = new Vector<>();
        for (Grade grade : grades) {
            if (grade.getUpgrade_after() <= years) {
                grades_valid.add(grade);
            }
        }
        return grades_valid;
    }

    public List<Grade> getAllGrade() {
        List<Grade> GradeList = gradeRepo.findAll();
        return GradeList;
    }

    // POST

    public void addEmployer(Employer employer) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (employer.getDate_embauche().getTime() > now.getTime()) {
            throw new RuntimeException("La date d'embauche ne pas se passé dans le futur");
        }

        employerRepo.save(employer);
    }

    public void addGrade(Grade grade) {
        if (grade.getUpgrade_after() < 0)
            throw new RuntimeException("Upgrade after can't be negative");
        if (grade.getUpgrade_salaire() < 0)
            throw new RuntimeException("Upgrade salary can't be negative");

        gradeRepo.save(grade);
    }

}
