package com.reglus.backend.repositories;


import com.reglus.backend.model.entities.rooms.StudyActivity;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.entities.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudyActivityRepository extends JpaRepository<StudyActivity, Long> {

    // Método para buscar sessões de estudo por estudante (Student)
    List<StudyActivity> findByStudent(Student student);

    // Método para buscar sessões de estudo por sala (Room)
    List<StudyActivity> findByRoom(Room room);

    // Método para buscar sessões de estudo por data (activityDate)
    List<StudyActivity> findByActivityDate(LocalDate activityDate);

    // Método para buscar sessões de estudo por intervalo de datas
    List<StudyActivity> findByActivityDateBetween(LocalDate startDate, LocalDate endDate);

    // Método para buscar sessões de estudo com duração maior ou igual a um valor
    List<StudyActivity> findByDurationHoursGreaterThanEqual(Double durationHours);

    // Método para buscar sessões de estudo com duração menor ou igual a um valor
    List<StudyActivity> findByDurationHoursLessThanEqual(Double durationHours);

    // Método para buscar sessões de estudo por pontos ganhos (pointsEarned)
    List<StudyActivity> findByPointsEarned(Integer pointsEarned);

    // Método para buscar sessões de estudo por estudante e sala
    List<StudyActivity> findByStudentAndRoom(Student student, Room room);

    // Método para buscar sessões de estudo por estudante e data
    List<StudyActivity> findByStudentAndActivityDate(Student student, LocalDate activityDate);

    // Método para buscar sessões de estudo por sala e data
    List<StudyActivity> findByRoomAndActivityDate(Room room, LocalDate activityDate);

    // Método para buscar sessões de estudo por estudante, sala e data
    List<StudyActivity> findByStudentAndRoomAndActivityDate(Student student, Room room, LocalDate activityDate);
}