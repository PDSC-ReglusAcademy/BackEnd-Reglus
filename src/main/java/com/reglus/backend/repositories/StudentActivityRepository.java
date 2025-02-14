package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.rooms.StudentActivity;
import com.reglus.backend.model.entities.rooms.Activity;
import com.reglus.backend.model.entities.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudentActivityRepository extends JpaRepository<StudentActivity, Long> {

    // Método para buscar submissões por estudante (Student)
    List<StudentActivity> findByStudent(Student student);

    // Método para buscar submissões por atividade (Activity)
    List<StudentActivity> findByActivity(Activity activity);

    // Método para buscar submissões por status
    List<StudentActivity> findByStatus(String status);

    // Método para buscar submissões por intervalo de datas de submissão
    List<StudentActivity> findBySubmissionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Método para buscar submissões com pontuação maior ou igual a um valor
    List<StudentActivity> findByPointsEarnedGreaterThanEqual(Integer pointsEarned);

    // Método para buscar submissões com pontuação menor ou igual a um valor
    List<StudentActivity> findByPointsEarnedLessThanEqual(Integer pointsEarned);

    // Método para buscar submissões por nota (grade)
    List<StudentActivity> findByGrade(String grade);

    // Método para buscar submissões que contenham feedback específico (usando LIKE para busca parcial)
    @Query("SELECT sa FROM StudentActivity sa WHERE sa.feedback LIKE %:feedback%")
    List<StudentActivity> findByFeedbackContaining(@Param("feedback") String feedback);

    // Método para buscar submissões por estudante e atividade
    List<StudentActivity> findByStudentAndActivity(Student student, Activity activity);

    // Método para buscar submissões por estudante e status
    List<StudentActivity> findByStudentAndStatus(Student student, String status);

    // Método para buscar submissões por atividade e status
    List<StudentActivity> findByActivityAndStatus(Activity activity, String status);
}