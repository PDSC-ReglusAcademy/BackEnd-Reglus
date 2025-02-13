package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Consulta por dia
    List<Schedule> findByDate(LocalDate date);

    // Consulta por semana
    List<Schedule> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Consulta por mês
    @Query("SELECT s FROM Schedule s WHERE YEAR(s.date) = :year AND MONTH(s.date) = :month")
    List<Schedule> findByMonth(@Param("year") int year, @Param("month") int month);

    // Consulta por ano
    @Query("SELECT s FROM Schedule s WHERE YEAR(s.date) = :year")
    List<Schedule> findByYear(@Param("year") int year);
}