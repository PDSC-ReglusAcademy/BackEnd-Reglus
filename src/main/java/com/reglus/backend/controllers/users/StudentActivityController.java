package com.reglus.backend.controllers.users;


import com.reglus.backend.model.entities.rooms.Activity;
import com.reglus.backend.model.entities.rooms.StudentActivity;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.ActivityRepository;
import com.reglus.backend.repositories.StudentActivityRepository;
import com.reglus.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student-activities")
@CrossOrigin(origins = "*")
public class StudentActivityController {

    @Autowired
    private StudentActivityRepository studentActivityRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ActivityRepository activityRepository;

    // Criar uma nova submissão de atividade
    @PostMapping
    public ResponseEntity<?> createStudentActivity(@RequestBody StudentActivity studentActivity) {
        try {
            studentActivity.setSubmissionDate(LocalDateTime.now());
            StudentActivity savedStudentActivity = studentActivityRepository.save(studentActivity);
            return new ResponseEntity<>(savedStudentActivity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Obter todas as submissões de atividades
    @GetMapping
    public ResponseEntity<List<StudentActivity>> getAllStudentActivities() {
        List<StudentActivity> studentActivities = studentActivityRepository.findAll();
        return new ResponseEntity<>(studentActivities, HttpStatus.OK);
    }

    // Obter uma submissão de atividade por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentActivityById(@PathVariable Long id) {
        Optional<StudentActivity> studentActivity = studentActivityRepository.findById(id);
        return studentActivity.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar uma submissão de atividade
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentActivity(@PathVariable Long id, @RequestBody StudentActivity updatedStudentActivity) {
        try {
            StudentActivity studentActivity = studentActivityRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Submissão de atividade não encontrada"));

            studentActivity.setPointsEarned(updatedStudentActivity.getPointsEarned());
            studentActivity.setGrade(updatedStudentActivity.getGrade());
            studentActivity.setFeedback(updatedStudentActivity.getFeedback());
            studentActivity.setStatus(updatedStudentActivity.getStatus());

            StudentActivity savedStudentActivity = studentActivityRepository.save(studentActivity);
            return new ResponseEntity<>(savedStudentActivity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Deletar uma submissão de atividade
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudentActivity(@PathVariable Long id) {
        try {
            if (!studentActivityRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            studentActivityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obter submissões de atividades por estudante (Student)
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentActivity>> getStudentActivitiesByStudent(@PathVariable Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.map(value -> new ResponseEntity<>(studentActivityRepository.findByStudent(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obter submissões de atividades por atividade (Activity)
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<StudentActivity>> getStudentActivitiesByActivity(@PathVariable Long activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        return activity.map(value -> new ResponseEntity<>(studentActivityRepository.findByActivity(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obter submissões de atividades por estudante e atividade
    @GetMapping("/student/{studentId}/activity/{activityId}")
    public ResponseEntity<List<StudentActivity>> getStudentActivitiesByStudentAndActivity(@PathVariable Long studentId, @PathVariable Long activityId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Activity> activity = activityRepository.findById(activityId);

        if (student.isPresent() && activity.isPresent()) {
            List<StudentActivity> activities = studentActivityRepository.findByStudentAndActivity(student.get(), activity.get());
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
