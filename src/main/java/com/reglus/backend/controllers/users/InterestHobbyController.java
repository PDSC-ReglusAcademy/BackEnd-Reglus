package com.reglus.backend.controllers.users;
import com.reglus.backend.model.entities.users.smf.InterestHobby;
import com.reglus.backend.model.entities.users.smf.InterestHobbyRequest;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.InterestHobbyRepository;
import com.reglus.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/interest-hobbies")
@CrossOrigin(origins = "*")
public class InterestHobbyController {

    @Autowired
    private InterestHobbyRepository interestHobbyRepository;

    @Autowired
    private StudentRepository studentRepository;

    /*
    @PostMapping
    public ResponseEntity<?> createInterestHobby(@RequestBody InterestHobbyRequest interestHobbyRequest) {
        try {
            Optional<Student> studentData = studentRepository.findById(interestHobbyRequest.getStudentId());
            if (!studentData.isPresent()) {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
            Student student = studentData.get();

            InterestHobby interestHobby = new InterestHobby();
            interestHobby.setStudent(student);
            interestHobby.setActivitiesOutsideSchool(interestHobbyRequest.getActivitiesOutsideSchool());
            interestHobby.setDreamsGoals(interestHobbyRequest.getDreamsGoals());
            interestHobbyRepository.save(interestHobby);

            student.setInterestHobby(interestHobby); // Assuming a setInterestHobby method exists
            studentRepository.save(student);

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    */

    @GetMapping
    public ResponseEntity<List<InterestHobby>> getAllInterestHobbies() {
        try {
            List<InterestHobby> interestHobbies = interestHobbyRepository.findAll();

            if (interestHobbies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(interestHobbies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterestHobby> getInterestHobbyById(@PathVariable("id") Long id) {
        Optional<InterestHobby> interestHobbyData = interestHobbyRepository.findById(id);
        return interestHobbyData.map(interestHobby -> new ResponseEntity<>(interestHobby, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterestHobby> updateInterestHobby(@PathVariable("id") Long id, @RequestBody InterestHobbyRequest interestHobbyRequest) {
        Optional<InterestHobby> interestHobbyData = interestHobbyRepository.findById(id);

        if (interestHobbyData.isPresent()) {
            InterestHobby existingInterestHobby = interestHobbyData.get();
            existingInterestHobby.setActivitiesOutsideSchool(interestHobbyRequest.getActivitiesOutsideSchool());
            existingInterestHobby.setDreamsGoals(interestHobbyRequest.getDreamsGoals());

            InterestHobby updatedInterestHobby = interestHobbyRepository.save(existingInterestHobby);
            return new ResponseEntity<>(updatedInterestHobby, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteInterestHobby(@PathVariable("id") Long id) {
        try {
            Optional<InterestHobby> interestHobbyData = interestHobbyRepository.findById(id);

            if (interestHobbyData.isPresent()) {
                InterestHobby interestHobby = interestHobbyData.get();
                Student student = interestHobby.getStudent();

                if (student != null) {
                    student.setInterestHobby(null); // Assuming a setInterestHobby method exists
                    studentRepository.save(student);
                }

                interestHobbyRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
