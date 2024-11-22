package com.reglus.backend.model.entities.users.smf;
import com.reglus.backend.model.enums.SelfPerformanceEvaluation;

public class SelfAssessmentRequest {
    private SelfPerformanceEvaluation performanceEvaluation;
    private String strengths;
    private String improvementAreas;

    // Getters and Setters
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

