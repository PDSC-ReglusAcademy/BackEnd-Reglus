package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.users.Educator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducatorRepository extends JpaRepository<Educator, Long> {
}