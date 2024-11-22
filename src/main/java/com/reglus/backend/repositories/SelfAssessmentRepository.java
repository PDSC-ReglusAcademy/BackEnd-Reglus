package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.users.smf.SelfAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfAssessmentRepository extends JpaRepository<SelfAssessment, Long> {
}

