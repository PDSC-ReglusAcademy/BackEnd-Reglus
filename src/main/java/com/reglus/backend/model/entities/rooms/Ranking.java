package com.reglus.backend.model.entities.rooms;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.reglus.backend.model.entities.users.Student;

@Entity
@Table(name = "rankings")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_id")
    private Long rankingId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "total_points", nullable = false)
    private Integer totalPoints;

    @Column(name = "total_hours", nullable = false)
    private Double totalHours;

    @Column(name = "ranking_date", nullable = false)
    private LocalDate rankingDate;

    @Column(name = "institution", length = 255)
    private String institution;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "city", length = 50)
    private String city;

    // Getters and Setters
    public Long getRankingId() {
        return rankingId;
    }

    public void setRankingId(Long rankingId) {
        this.rankingId = rankingId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public LocalDate getRankingDate() {
        return rankingDate;
    }

    public void setRankingDate(LocalDate rankingDate) {
        this.rankingDate = rankingDate;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
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