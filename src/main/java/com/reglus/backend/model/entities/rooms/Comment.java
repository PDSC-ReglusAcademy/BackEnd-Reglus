package com.reglus.backend.model.entities.rooms;

import com.reglus.backend.model.entities.users.Student;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isPublic;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comment() {}

    public Comment(Room room, Student student, String content, boolean isPublic) {
        this.room = room;
        this.student = student;
        this.content = content;
        this.isPublic = isPublic;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return commentId; }
    public Room getRoom() { return room; }
    public Student getStudent() { return student; }
    public String getContent() { return content; }
    public boolean isPublic() { return isPublic; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setRoom(Room room) { this.room = room; }
    public void setStudent(Student student) { this.student = student; }
    public void setContent(String content) { this.content = content; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setId(Long id) {
        this.commentId = id;
    }
}
