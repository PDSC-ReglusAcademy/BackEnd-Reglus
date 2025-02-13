package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.schedule.Schedule;
import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.model.entities.users.Educator;
import com.reglus.backend.model.entities.users.EducatorRequest;
import com.reglus.backend.repositories.EducatorRepository;
import com.reglus.backend.repositories.ScheduleRepository;
import com.reglus.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/educators")
@CrossOrigin(origins = "*")
public class EducatorController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EducatorRepository educatorRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o PasswordEncoder

    @PostMapping
    public ResponseEntity<?> createEducator(@RequestBody EducatorRequest educatorRequest) {
        try {
            User user = new User();
            user.setUserType(educatorRequest.getUserType());
            user.setEmail(educatorRequest.getEmail());
            user.setPasswordHash(passwordEncoder.encode(educatorRequest.getPasswordHash()));
            user.setName(educatorRequest.getName());
            user.setDateBirth(educatorRequest.getDateBirth());
            user.setGender(educatorRequest.getGender());
            user.setDisability(educatorRequest.getDisability());
            user.setEducationLevel(educatorRequest.getEducationLevel());
            user.setInstituteName(educatorRequest.getInstituteName());
            user.setCity(educatorRequest.getCity());
            user.setState(educatorRequest.getState());
            userRepository.save(user);

            Educator educator = new Educator();
            educator.setUser(user);
            educator.setExperienceYears(educatorRequest.getExperienceYears());
            educator.setBio(educatorRequest.getBio());
            educatorRepository.save(educator);

            return new ResponseEntity<>(educator, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Educator>> getAllEducators() {
        try {
            List<Educator> educators = educatorRepository.findAll();
            if (educators.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(educators, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Educator> getEducatorById(@PathVariable("id") Long id) {
        Optional<Educator> educatorData = educatorRepository.findById(id);

        if (educatorData.isPresent()) {
            return new ResponseEntity<>(educatorData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEducator(@PathVariable("id") Long id, @RequestBody EducatorRequest educatorRequest) {
        try {
            Optional<Educator> educatorData = educatorRepository.findById(id);

            if (educatorData.isPresent()) {
                Educator educator = educatorData.get();

                Optional<User> optionalUser = userRepository.findById(educator.getUser().getUserId());
                if (!optionalUser.isPresent()) {
                    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }

                User user = optionalUser.get();

                user.setUserType(educatorRequest.getUserType());
                user.setEmail(educatorRequest.getEmail());
                user.setPasswordHash(passwordEncoder.encode(educatorRequest.getPasswordHash()));
                user.setName(educatorRequest.getName());
                user.setDateBirth(educatorRequest.getDateBirth());
                user.setGender(educatorRequest.getGender());
                user.setDisability(educatorRequest.getDisability());
                user.setEducationLevel(educatorRequest.getEducationLevel());
                user.setInstituteName(educatorRequest.getInstituteName());
                userRepository.save(user);

                educator.setUser(user);
                educator.setExperienceYears(educatorRequest.getExperienceYears());
                educator.setBio(educatorRequest.getBio());
                educatorRepository.save(educator);

                return new ResponseEntity<>(educator, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Educator not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEducator(@PathVariable("id") Long id) {
        try {
            Optional<Educator> educatorData = educatorRepository.findById(id);

            if (educatorData.isPresent()) {
                Educator educator = educatorData.get();
                User user = educator.getUser();

                educatorRepository.deleteById(id);
                userRepository.delete(user);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}