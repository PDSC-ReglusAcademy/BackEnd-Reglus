package com.reglus.backend.model.entities.rooms;

import com.reglus.backend.model.entities.users.Educator;
import com.reglus.backend.model.entities.users.Student;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress_records")
public class ProgressRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long progressId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "educator_id", nullable = false)
    private Educator educator;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "goals", columnDefinition = "TEXT")
    private String goals;

    @Column(name = "action_plans", columnDefinition = "TEXT")
    private String actionPlans;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Default constructor
    public ProgressRecord() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor with parameters
    public ProgressRecord(Student student, Educator educator, String observations, String goals, String actionPlans) {
        this.student = student;
        this.educator = educator;
        this.observations = observations;
        this.goals = goals;
        this.actionPlans = actionPlans;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getProgressId() {
        return progressId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Educator getEducator() {
        return educator;
    }

    public void setEducator(Educator educator) {
        this.educator = educator;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getActionPlans() {
        return actionPlans;
    }

    public void setActionPlans(String actionPlans) {
        this.actionPlans = actionPlans;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}