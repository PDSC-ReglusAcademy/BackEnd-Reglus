package com.reglus.backend.repositories;


import com.reglus.backend.model.entities.rooms.Activity;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.users.Educator;
import com.reglus.backend.model.entities.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // Método para buscar atividades por sala (Room)
    List<Activity> findByRoom(Room room);

    // Método para buscar atividades por educador (Educator)
    List<Activity> findByEducator(Educator educator);

    // Método para buscar atividades por cronograma (Schedule)
    List<Activity> findBySchedule(Schedule schedule);

    // Método para buscar atividades com data de vencimento antes de uma determinada data
    List<Activity> findByDueDateBefore(LocalDateTime dueDate);

    // Método para buscar atividades com data de vencimento após uma determinada data
    List<Activity> findByDueDateAfter(LocalDateTime dueDate);

    // Método para buscar atividades por título (usando LIKE para busca parcial)
    @Query("SELECT a FROM Activity a WHERE a.title LIKE %:title%")
    List<Activity> findByTitleContaining(@Param("title") String title);

    // Método para buscar atividades por intervalo de datas de criação
    List<Activity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Método para buscar atividades por intervalo de datas de atualização
    List<Activity> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Método para buscar atividades por pontuação máxima maior ou igual a um valor
    List<Activity> findByMaxPointsGreaterThanEqual(Integer maxPoints);

    // Método para buscar atividades por pontuação máxima menor ou igual a um valor
    List<Activity> findByMaxPointsLessThanEqual(Integer maxPoints);
}