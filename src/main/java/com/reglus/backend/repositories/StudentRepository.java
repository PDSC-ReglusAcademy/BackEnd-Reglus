package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user); // Método para buscar um Student pelo User
}