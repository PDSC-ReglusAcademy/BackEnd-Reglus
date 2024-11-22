package com.reglus.backend.model.entities.users.smf;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.reglus.backend.model.entities.users.Student;
import jakarta.persistence.*;
import com.reglus.backend.model.enums.SelfPerformanceEvaluation;

@Entity
@Table(name = "smf_self_assessment")
public class SelfAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessmentId;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @Enumerated(EnumType.STRING)
    private SelfPerformanceEvaluation performanceEvaluation;

    @Column(length = 255)
    private String strengths;

    @Column(length = 255)
    private String improvementAreas;

    // Getters and Setters
    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SelfPerformanceEvaluation getPerformanceEvaluation() {
        return performanceEvaluation;
    }

    public void setPerformanceEvaluation(SelfPerformanceEvaluation performanceEvaluation) {
        this.performanceEvaluation = performanceEvaluation;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getImprovementAreas() {
        return improvementAreas;
    }

    public void setImprovementAreas(String improvementAreas) {
        this.improvementAreas = improvementAreas;
    }
}

