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

    /*
    @PostMapping
    public ResponseEntity<?> createHealthWellbeing(@RequestBody HealthWellbeingRequest request) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(request.getStudentId());
            if (!optionalStudent.isPresent()) {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }

            Student student = optionalStudent.get();

            HealthWellbeing healthWellbeing = new HealthWellbeing();
            healthWellbeing.setStudent(student);
            healthWellbeing.setHealthCondition(request.getHealthCondition());
            healthWellbeing.setPhysicalActivity(request.getPhysicalActivity());
            healthWellbeing.setDietaryEvaluation(request.getDietaryEvaluation());
            healthWellbeing.setSleepHours(request.getSleepHours());
            healthWellbeingRepository.save(healthWellbeing);

            // Atualizar o estudante com o novo healthWellbeing
            student.setHealthWellbeing(healthWellbeing);
            studentRepository.save(student);  // Salva o estudante com a referência atualizada

            return new ResponseEntity<>(healthWellbeing, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    */

    @GetMapping
    public ResponseEntity<List<HealthWellbeing>> getAllHealthWellbeing() {
        try {
            List<HealthWellbeing> healthWellbeings = healthWellbeingRepository.findAll();
            if (healthWellbeings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(healthWellbeings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthWellbeing> getHealthWellbeingById(@PathVariable("id") Long id) {
        Optional<HealthWellbeing> healthWellbeingData = healthWellbeingRepository.findById(id);
        return healthWellbeingData.map(healthWellbeing -> new ResponseEntity<>(healthWellbeing, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<HealthWellbeing> updateHealthWellbeing(@PathVariable("id") Long id, @RequestBody HealthWellbeing healthWellbeing) {
        Optional<HealthWellbeing> healthWellbeingData = healthWellbeingRepository.findById(id);

        if (healthWellbeingData.isPresent()) {
            HealthWellbeing existingHealthWellbeing = healthWellbeingData.get();
            existingHealthWellbeing.setHealthCondition(healthWellbeing.getHealthCondition());
            existingHealthWellbeing.setPhysicalActivity(healthWellbeing.getPhysicalActivity());
            existingHealthWellbeing.setDietaryEvaluation(healthWellbeing.getDietaryEvaluation());
            existingHealthWellbeing.setSleepHours(healthWellbeing.getSleepHours());

            HealthWellbeing updatedHealthWellbeing = healthWellbeingRepository.save(existingHealthWellbeing);
            return new ResponseEntity<>(updatedHealthWellbeing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHealthWellbeing(@PathVariable("id") Long id) {
        try {
            Optional<HealthWellbeing> healthWellbeingData = healthWellbeingRepository.findById(id);

            if (healthWellbeingData.isPresent()) {
                HealthWellbeing healthWellbeing = healthWellbeingData.get();
                Student student = healthWellbeing.getStudent();

                if(student != null){
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
