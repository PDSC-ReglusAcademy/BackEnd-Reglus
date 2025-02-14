package com.reglus.backend.model.entities.rooms;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.entities.rooms.Room;

//Registra sessões de estudo de um estudante
//Ligada a um estudante e uma sala
@Entity
@Table(name = "study_activities")
public class StudyActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long studyId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "activity_date", nullable = false)
    private LocalDate activityDate;

    @Column(name = "duration_hours", nullable = false)
    private Double durationHours;

    @Column(name = "points_earned")
    private Integer pointsEarned;

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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public Double getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Double durationHours) {
        this.durationHours = durationHours;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
    }
}