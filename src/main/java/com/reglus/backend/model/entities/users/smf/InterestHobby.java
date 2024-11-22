package com.reglus.backend.model.entities.users.smf;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.reglus.backend.model.entities.users.Student;
import jakarta.persistence.*;

@Entity
@Table(name = "smf_interests_hobbies")
public class InterestHobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hobbyId;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @Column(length = 255)
    private String activitiesOutsideSchool;

    @Column(length = 500)
    private String dreamsGoals;

    // Getters and Setters
    public Long getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(Long hobbyId) {
        this.hobbyId = hobbyId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getActivitiesOutsideSchool() {
        return activitiesOutsideSchool;
    }

    public void setActivitiesOutsideSchool(String activitiesOutsideSchool) {
        this.activitiesOutsideSchool = activitiesOutsideSchool;
    }

    public String getDreamsGoals() {
        return dreamsGoals;
    }

    public void setDreamsGoals(String dreamsGoals) {
        this.dreamsGoals = dreamsGoals;
    }
}
