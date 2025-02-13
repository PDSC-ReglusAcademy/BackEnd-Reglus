package com.reglus.backend.model.entities.schedule;

import java.time.LocalDate;
import java.util.Set;

public class ScheduleRequest {
    private String title;
    private String description;
    private LocalDate date;
    private Long educatorId;
    private Long studentId;
    private Set<Long> sharedUserIds;
    private EventType eventType;

    // Getters e Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getEducatorId() {
        return educatorId;
    }

    public void setEducatorId(Long educatorId) {
        this.educatorId = educatorId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Set<Long> getSharedUserIds() {
        return sharedUserIds;
    }

    public void setSharedUserIds(Set<Long> sharedUserIds) {
        this.sharedUserIds = sharedUserIds;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}