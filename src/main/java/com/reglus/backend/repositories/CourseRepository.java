package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.rooms.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}