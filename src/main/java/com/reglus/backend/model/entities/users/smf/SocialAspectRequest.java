package com.reglus.backend.model.entities.users.smf;

import com.reglus.backend.model.enums.SocialRelationship;

public class SocialAspectRequest {
    private String livingWith;
    private SocialRelationship relationshipWithClassmates;
    private SocialRelationship relationshipWithTeachers;
    private SocialRelationship relationshipWithFamily;

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
