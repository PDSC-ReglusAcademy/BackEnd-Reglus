package com.reglus.backend.model.entities.users.smf;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.reglus.backend.model.entities.users.Student;
import jakarta.persistence.*;
import com.reglus.backend.model.enums.SocialRelationship;

@Entity
@Table(name = "smf_social_aspects")
public class SocialAspect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialId;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @Column(length = 255)
    private String livingWith = "undefined";

    @Enumerated(EnumType.STRING)
    private SocialRelationship relationshipWithClassmates;

    @Enumerated(EnumType.STRING)
    private SocialRelationship relationshipWithTeachers;

    @Enumerated(EnumType.STRING)
    private SocialRelationship relationshipWithFamily;

    // Getters and Setters
    public Long getSocialId() {
        return socialId;
    }

    public void setSocialId(Long socialId) {
        this.socialId = socialId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getLivingWith() {
        return livingWith;
    }

    public void setLivingWith(String livingWith) {
        this.livingWith = livingWith;
    }

    public SocialRelationship getRelationshipWithClassmates() {
        return relationshipWithClassmates;
    }

    public void setRelationshipWithClassmates(SocialRelationship relationshipWithClassmates) {
        this.relationshipWithClassmates = relationshipWithClassmates;
    }

    public SocialRelationship getRelationshipWithTeachers() {
        return relationshipWithTeachers;
    }

    public void setRelationshipWithTeachers(SocialRelationship relationshipWithTeachers) {
        this.relationshipWithTeachers = relationshipWithTeachers;
    }

    public SocialRelationship getRelationshipWithFamily() {
        return relationshipWithFamily;
    }

    public void setRelationshipWithFamily(SocialRelationship relationshipWithFamily) {
        this.relationshipWithFamily = relationshipWithFamily;
    }
}
