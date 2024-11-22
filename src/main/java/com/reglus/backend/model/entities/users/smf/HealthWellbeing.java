package com.reglus.backend.model.entities.users.smf;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.enums.HealthDietaryEvaluation;
import com.reglus.backend.model.enums.HealthSleepHours;
import jakarta.persistence.*;

@Entity
@Table(name = "smf_health_wellbeing")
public class HealthWellbeing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long healthId;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @Column(length = 255)
    private String healthCondition;

    @Column(length = 255)
    private String physicalActivity;

    @Enumerated(EnumType.STRING)
    private HealthDietaryEvaluation dietaryEvaluation;

    @Enumerated(EnumType.STRING)
    private HealthSleepHours sleepHours;

    // Getters and Setters
    public Long getHealthId() {
        return healthId;
    }

    public void setHealthId(Long healthId) {
        this.healthId = healthId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(String physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public HealthDietaryEvaluation getDietaryEvaluation() {
        return dietaryEvaluation;
    }

    public void setDietaryEvaluation(HealthDietaryEvaluation dietaryEvaluation) {
        this.dietaryEvaluation = dietaryEvaluation;
    }

    public HealthSleepHours getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(HealthSleepHours sleepHours) {
        this.sleepHours = sleepHours;
    }
}
