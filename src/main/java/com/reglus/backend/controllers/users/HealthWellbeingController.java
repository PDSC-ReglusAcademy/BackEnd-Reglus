package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.users.smf.HealthWellbeing;
import com.reglus.backend.model.entities.users.smf.HealthWellbeingRequest;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.HealthWellbeingRepository;
import com.reglus.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/healthwellbeing")
@CrossOrigin(origins = "*")
public class HealthWellbeingController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private HealthWellbeingRepository healthWellbeingRepository;

    /*@PostMapping
    public ResponseEntity<?> createHealthWellbeing(@RequestBody HealthWellbeingRequest request) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(request.getStudentId());

            if (!optionalStudent.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
            }

            Student student = optionalStudent.get();

            HealthWellbeing healthWellbeing = new HealthWellbeing();
            healthWellbeing.setStudent(student);
            healthWellbeing.setHealthCondition(request.getHealthCondition());
            healthWellbeing.setPhysicalActivity(request.getPhysicalActivity());
            healthWellbeing.setDietaryEvaluation(request.getDietaryEvaluation());
            healthWellbeing.setSleepHours(request.getSleepHours());

            // Salva primeiro o HealthWellbeing
            healthWellbeingRepository.save(healthWellbeing);

            // Atualiza o estudante com o novo relacionamento e salva no banco
            student.setHealthWellbeing(healthWellbeing);
            studentRepository.save(student);

            return new ResponseEntity<>(healthWellbeing, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar registro: " + e.getMessage());
        }
    }*/

    @GetMapping
    public ResponseEntity<List<HealthWellbeing>> getAllHealthWellbeing() {
        try {
            List<HealthWellbeing> healthWellbeings = healthWellbeingRepository.findAll();
            return healthWellbeings.isEmpty()
                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(healthWellbeings, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthWellbeing> getHealthWellbeingById(@PathVariable("id") Long id) {
        Optional<HealthWellbeing> healthWellbeing = healthWellbeingRepository.findById(id);
        return healthWellbeing.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHealthWellbeing(@PathVariable("id") Long id, @RequestBody HealthWellbeing healthWellbeing) {
        Optional<HealthWellbeing> existingHealthWellbeing = healthWellbeingRepository.findById(id);

        if (existingHealthWellbeing.isPresent()) {
            HealthWellbeing updatedHealthWellbeing = existingHealthWellbeing.get();
            updatedHealthWellbeing.setHealthCondition(healthWellbeing.getHealthCondition());
            updatedHealthWellbeing.setPhysicalActivity(healthWellbeing.getPhysicalActivity());
            updatedHealthWellbeing.setDietaryEvaluation(healthWellbeing.getDietaryEvaluation());
            updatedHealthWellbeing.setSleepHours(healthWellbeing.getSleepHours());

            return new ResponseEntity<>(healthWellbeingRepository.save(updatedHealthWellbeing), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHealthWellbeing(@PathVariable("id") Long id) {
        try {
            Optional<HealthWellbeing> existingHealthWellbeing = healthWellbeingRepository.findById(id);

            if (existingHealthWellbeing.isPresent()) {
                HealthWellbeing healthWellbeing = existingHealthWellbeing.get();

                // Remove a referência do estudante antes de deletar
                Student student = healthWellbeing.getStudent();
                if (student != null) {
                    student.setHealthWellbeing(null);
                    studentRepository.save(student);
                }

                healthWellbeingRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
