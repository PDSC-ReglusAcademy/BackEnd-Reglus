package com.reglus.backend.model.entities.users.smf;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.reglus.backend.model.entities.users.Student;
import jakarta.persistence.*;
import com.reglus.backend.model.enums.StudyHoursPerDay;

@Entity
@Table(name = "smf_study_habits")
public class StudyHabit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @Column(length = 100)
    private String studyMethods;

    @Enumerated(EnumType.STRING)
    private StudyHoursPerDay studyHoursPerDay;

    @Column(length = 100)
    private String studyLocations;

    @Column(length = 255)
    private String studyPlan;

    // Getters and Setters
    public Long getStudyId() {
        return studyId;
    }

    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStudyMethods() {
        return studyMethods;
    }

    public void setStudyMethods(String studyMethods) {
        this.studyMethods = studyMethods;
    }

    public StudyHoursPerDay getStudyHoursPerDay() {
        return studyHoursPerDay;
    }

    public void setStudyHoursPerDay(StudyHoursPerDay studyHoursPerDay) {
        this.studyHoursPerDay = studyHoursPerDay;
    }

    public String getStudyLocations() {
        return studyLocations;
    }

    public void setStudyLocations(String studyLocations) {
        this.studyLocations = studyLocations;
    }

    public String getStudyPlan() {
        return studyPlan;
    }

    public void setStudyPlan(String studyPlan) {
        this.studyPlan = studyPlan;
    }
}
