package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.users.smf.StudyHabit;
import com.reglus.backend.model.entities.users.smf.StudyHabitRequest;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.StudyHabitRepository;
import com.reglus.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/study-habits")
@CrossOrigin(origins = "*")
public class StudyHabitController {

    @Autowired
    private StudyHabitRepository studyHabitRepository;

    @Autowired
    private StudentRepository studentRepository;

    /*
    @PostMapping
    public ResponseEntity<?> createStudyHabit(@RequestBody StudyHabitRequest studyHabitRequest) {
        try {
            Optional<Student> studentData = studentRepository.findById(studyHabitRequest.getStudentId());
            if (!studentData.isPresent()) {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
            Student student = studentData.get();

            StudyHabit studyHabit = new StudyHabit();
            studyHabit.setStudent(student);
            studyHabit.setStudyMethods(studyHabitRequest.getStudyMethods());
            studyHabit.setStudyHoursPerDay(studyHabitRequest.getStudyHoursPerDay());
            studyHabit.setStudyLocations(studyHabitRequest.getStudyLocations());
            studyHabit.setStudyPlan(studyHabitRequest.getStudyPlan());
            studyHabitRepository.save(studyHabit);

            student.setStudyHabit(studyHabit); // Assuming a setStudyHabit method exists
            studentRepository.save(student);

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    */

    @GetMapping
    public ResponseEntity<List<StudyHabit>> getAllStudyHabits() {
        try {
            List<StudyHabit> studyHabits = studyHabitRepository.findAll();

            if (studyHabits.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(studyHabits, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyHabit> getStudyHabitById(@PathVariable("id") Long id) {
        Optional<StudyHabit> studyHabitData = studyHabitRepository.findById(id);
        return studyHabitData.map(studyHabit -> new ResponseEntity<>(studyHabit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyHabit> updateStudyHabit(@PathVariable("id") Long id, @RequestBody StudyHabitRequest studyHabitRequest) {
        Optional<StudyHabit> studyHabitData = studyHabitRepository.findById(id);

        if (studyHabitData.isPresent()) {
            StudyHabit existingStudyHabit = studyHabitData.get();
            existingStudyHabit.setStudyMethods(studyHabitRequest.getStudyMethods());
            existingStudyHabit.setStudyHoursPerDay(studyHabitRequest.getStudyHoursPerDay());
            existingStudyHabit.setStudyLocations(studyHabitRequest.getStudyLocations());
            existingStudyHabit.setStudyPlan(studyHabitRequest.getStudyPlan());

            StudyHabit updatedStudyHabit = studyHabitRepository.save(existingStudyHabit);
            return new ResponseEntity<>(updatedStudyHabit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudyHabit(@PathVariable("id") Long id) {
        try {
            Optional<StudyHabit> studyHabitData = studyHabitRepository.findById(id);

            if (studyHabitData.isPresent()) {
                StudyHabit studyHabit = studyHabitData.get();
                Student student = studyHabit.getStudent();

                if (student != null) {
                    student.setStudyHabit(null); // Assuming a setStudyHabit method exists
                    studentRepository.save(student);
                }

                studyHabitRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
