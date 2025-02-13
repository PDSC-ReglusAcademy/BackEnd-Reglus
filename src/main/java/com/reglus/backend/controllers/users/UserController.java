package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.schedule.Schedule;
import com.reglus.backend.model.entities.users.Educator;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.model.enums.UserType;
import com.reglus.backend.repositories.EducatorRepository;
import com.reglus.backend.repositories.ScheduleRepository;
import com.reglus.backend.repositories.StudentRepository;
import com.reglus.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EducatorRepository educatorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {

            userRepository.save(user);

            // Cria a agenda com base no tipo de usuário
            Schedule schedule = new Schedule();
            schedule.setCreatedAt(LocalDateTime.now());
            schedule.setUpdatedAt(LocalDateTime.now());

            if (user.getUserType() == UserType.EDUCATOR) {
                Optional<Educator> educatorOpt = educatorRepository.findByUser(user);
                if (educatorOpt.isPresent()) {
                    schedule.setEducator(educatorOpt.get().getUser()); // Associa a agenda ao educador
                } else {
                    return new ResponseEntity<>("Educator not found for the given user", HttpStatus.NOT_FOUND);
                }
            } else if (user.getUserType() == UserType.STUDENT) {
                Optional<Student> studentOpt = studentRepository.findByUser(user);
                if (studentOpt.isPresent()) {
                    schedule.setStudent(studentOpt.get().getUser()); // Associa a agenda ao estudante
                } else {
                    return new ResponseEntity<>("Student not found for the given user", HttpStatus.NOT_FOUND);
                }
            }

            scheduleRepository.save(schedule);

            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        Optional<User> userData = userRepository.findByEmail(email);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
