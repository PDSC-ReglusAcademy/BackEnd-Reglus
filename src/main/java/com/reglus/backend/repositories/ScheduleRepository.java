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

    // Consulta para buscar agendamentos por data
    List<Schedule> findByDate(LocalDate date);

    // Consulta para buscar agendamentos por semana
    @Query("SELECT s FROM Schedule s WHERE s.date BETWEEN :startDate AND :endDate")
    List<Schedule> findByWeek(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Consulta para buscar agendamentos por mês
    @Query("SELECT s FROM Schedule s WHERE YEAR(s.date) = :year AND MONTH(s.date) = :month")
    List<Schedule> findByMonth(@Param("year") int year, @Param("month") int month);
}