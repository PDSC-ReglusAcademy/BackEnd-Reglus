package com.reglus.backend.model.entities.rooms;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.users.Student;

@Entity
@Table(name = "room_students")
public class RoomStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_student_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "completion_status", length = 50)
    private String completionStatus;

    // Getters and Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }
}