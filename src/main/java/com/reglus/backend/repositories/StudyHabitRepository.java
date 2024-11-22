package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.users.smf.StudyHabit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyHabitRepository extends JpaRepository<StudyHabit, Long> {
}
