package com.reglus.backend.model.entities.users;

import jakarta.persistence.*;

@Entity
@Table(name = "educators")
public class Educator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long educatorId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "experience_years", length = 50)
    private String experienceYears;

    @Column(length = 500)
    private String bio;

    // Construtor
    public Educator() {

    }

    public Educator(User user, String experienceYears, String bio) {
        this.user = user;
        this.experienceYears = experienceYears;
        this.bio = bio;
    }

    // Getters and Setters
    public Long getEducatorId() {
        return educatorId;
    }

    public void setEducatorId(Long educatorId) {
        this.educatorId = educatorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(String experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}