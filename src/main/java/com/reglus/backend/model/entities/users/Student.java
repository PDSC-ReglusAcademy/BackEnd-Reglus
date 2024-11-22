package com.reglus.backend.model.entities.users;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.reglus.backend.model.entities.users.smf.*;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "social_id")
    @JsonManagedReference
    private SocialAspect socialAspect;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "health_id")
    @JsonManagedReference
    private HealthWellbeing healthWellbeing;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hobbies_id")
    @JsonManagedReference
    private InterestHobby interestHobby;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "study_id")
    @JsonManagedReference
    private StudyHabit studyHabit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assessment_id")
    @JsonManagedReference
    private SelfAssessment selfAssessment;

    @Column(length = 500)
    private String finalObservations;

    @Column(length = 50)
    private String state;

    @Column(length = 50)
    private String city;

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SocialAspect getSocialAspect() {
        return socialAspect;
    }

    public void setSocialAspect(SocialAspect socialAspect) {
        this.socialAspect = socialAspect;
    }

    public HealthWellbeing getHealthWellbeing() {
        return healthWellbeing;
    }

    public void setHealthWellbeing(HealthWellbeing healthWellbeing) {
        this.healthWellbeing = healthWellbeing;
    }

    public InterestHobby getInterestHobby() {
        return interestHobby;
    }

    public void setInterestHobby(InterestHobby interestHobby) {
        this.interestHobby = interestHobby;
    }

    public StudyHabit getStudyHabit() {
        return studyHabit;
    }

    public void setStudyHabit(StudyHabit studyHabit) {
        this.studyHabit = studyHabit;
    }

    public SelfAssessment getSelfAssessment() {
        return selfAssessment;
    }

    public void setSelfAssessment(SelfAssessment selfAssessment) {
        this.selfAssessment = selfAssessment;
    }

    public String getFinalObservations() {
        return finalObservations;
    }

    public void setFinalObservations(String finalObservations) {
        this.finalObservations = finalObservations;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}