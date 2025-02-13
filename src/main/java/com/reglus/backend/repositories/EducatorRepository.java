package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.users.Educator;
import com.reglus.backend.model.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducatorRepository extends JpaRepository<Educator, Long> {
    Optional<Educator> findByUser(User user); // Método para buscar um Educator pelo User
}