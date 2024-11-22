package com.reglus.backend.controllers.users;
import com.reglus.backend.model.entities.users.smf.SelfAssessment;
import com.reglus.backend.model.entities.users.smf.SelfAssessmentRequest;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.SelfAssessmentRepository;
import com.reglus.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/self-assessments")
@CrossOrigin(origins = "*")
public class SelfAssessmentController {

    @Autowired
    private SelfAssessmentRepository selfAssessmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    /*
    @PostMapping
    public ResponseEntity<?> createSelfAssessment(@RequestBody SelfAssessmentRequest selfAssessmentRequest) {
        try {
            Optional<Student> studentData = studentRepository.findById(selfAssessmentRequest.getStudentId());
            if (!studentData.isPresent()) {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
            Student student = studentData.get();

            SelfAssessment selfAssessment = new SelfAssessment();
            selfAssessment.setStudent(student);
            selfAssessment.setPerformanceEvaluation(selfAssessmentRequest.getPerformanceEvaluation());
            selfAssessment.setStrengths(selfAssessmentRequest.getStrengths());
            selfAssessment.setImprovementAreas(selfAssessmentRequest.getImprovementAreas());
            selfAssessmentRepository.save(selfAssessment);

            student.setSelfAssessment(selfAssessment); // Assuming a setSelfAssessment method exists
            studentRepository.save(student);

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    */

    @GetMapping
    public ResponseEntity<List<SelfAssessment>> getAllSelfAssessments() {
        try {
            List<SelfAssessment> selfAssessments = selfAssessmentRepository.findAll();

            if (selfAssessments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(selfAssessments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SelfAssessment> getSelfAssessmentById(@PathVariable("id") Long id) {
        Optional<SelfAssessment> selfAssessmentData = selfAssessmentRepository.findById(id);
        return selfAssessmentData.map(selfAssessment -> new ResponseEntity<>(selfAssessment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SelfAssessment> updateSelfAssessment(@PathVariable("id") Long id, @RequestBody SelfAssessmentRequest selfAssessmentRequest) {
        Optional<SelfAssessment> selfAssessmentData = selfAssessmentRepository.findById(id);

        if (selfAssessmentData.isPresent()) {
            SelfAssessment existingSelfAssessment = selfAssessmentData.get();
            existingSelfAssessment.setPerformanceEvaluation(selfAssessmentRequest.getPerformanceEvaluation());
            existingSelfAssessment.setStrengths(selfAssessmentRequest.getStrengths());
            existingSelfAssessment.setImprovementAreas(selfAssessmentRequest.getImprovementAreas());

            SelfAssessment updatedSelfAssessment = selfAssessmentRepository.save(existingSelfAssessment);
            return new ResponseEntity<>(updatedSelfAssessment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSelfAssessment(@PathVariable("id") Long id) {
        try {
            Optional<SelfAssessment> selfAssessmentData = selfAssessmentRepository.findById(id);

            if (selfAssessmentData.isPresent()) {
                SelfAssessment selfAssessment = selfAssessmentData.get();
                Student student = selfAssessment.getStudent();

                if (student != null) {
                    student.setSelfAssessment(null); // Assuming a setSelfAssessment method exists
                    studentRepository.save(student);
                }

                selfAssessmentRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
