package com.reglus.backend.model.entities.users;

import com.reglus.backend.model.entities.users.smf.*;

import java.time.LocalDate;

public class StudentRequest {
    private String email;
    private String passwordHash;
    private String name;
    private LocalDate dateBirth;
    private String gender;
    private String disability;
    private String educationLevel;
    private String instituteName;
    private String state;
    private String city;
    private String finalObservations;

    private SocialAspectRequest socialAspectRequest;

    private StudyHabitRequest studyHabitRequest;

    private HealthWellbeingRequest healthWellbeingRequest;

    private InterestHobbyRequest interestHobbyRequest;

    private SelfAssessmentRequest selfAssessmentRequest;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
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

    public String getFinalObservations() {
        return finalObservations;
    }

    public void setFinalObservations(String finalObservations) {
        this.finalObservations = finalObservations;
    }

    public SocialAspectRequest getSocialAspectRequest() {
        return socialAspectRequest;
    }

    public void setSocialAspectRequest(SocialAspectRequest socialAspectRequest) {
        this.socialAspectRequest = socialAspectRequest;
    }

    public StudyHabitRequest getStudyHabitRequest() {
        return studyHabitRequest;
    }

    public void setStudyHabitRequest(StudyHabitRequest studyHabitRequest) {
        this.studyHabitRequest = studyHabitRequest;
    }

    public HealthWellbeingRequest getHealthWellbeingRequest() {
        return healthWellbeingRequest;
    }

    public void setHealthWellbeingRequest(HealthWellbeingRequest healthWellbeingRequest) {
        this.healthWellbeingRequest = healthWellbeingRequest;
    }

    public InterestHobbyRequest getInterestHobbyRequest() {
        return interestHobbyRequest;
    }

    public void setInterestHobbyRequest(InterestHobbyRequest interestHobbyRequest) {
        this.interestHobbyRequest = interestHobbyRequest;
    }

    public SelfAssessmentRequest getSelfAssessmentRequest() {
        return selfAssessmentRequest;
    }

    public void setSelfAssessmentRequest(SelfAssessmentRequest selfAssessmentRequest) {
        this.selfAssessmentRequest = selfAssessmentRequest;
    }
}
