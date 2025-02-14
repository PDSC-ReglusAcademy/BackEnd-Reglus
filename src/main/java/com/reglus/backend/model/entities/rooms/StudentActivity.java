package com.reglus.backend.model.entities.rooms;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.reglus.backend.model.entities.rooms.Activity;
import com.reglus.backend.model.entities.users.Student;
//Representa a submissão de uma atividade feita por um estudante
//Ligada a uma atividade e a um estudante
@Entity
@Table(name = "student_activities")
public class StudentActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_activity_id")
    private Long studentActivityId;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;

    @Column(name = "points_earned")
    private Integer pointsEarned;

    @Column(name = "grade", length = 10)
    private String grade;

    @Column(name = "feedback", length = 1000)
    private String feedback;

    @Column(name = "status", length = 50)
    private String status;

    // Getters and Setters
    public Long getStudentActivityId() {
        return studentActivityId;
    }

    public void setStudentActivityId(Long studentActivityId) {
        this.studentActivityId = studentActivityId;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
