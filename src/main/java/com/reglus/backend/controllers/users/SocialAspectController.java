package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.users.smf.SocialAspect;
import com.reglus.backend.model.entities.users.smf.SocialAspectRequest;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.SocialAspectRepository;
import com.reglus.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/social-aspects")
@CrossOrigin(origins = "*")
public class SocialAspectController {
    @Autowired
    private SocialAspectRepository socialAspectRepository;

    @Autowired
    private StudentRepository studentRepository;

    /*
    @PostMapping
    public ResponseEntity<?> createSocialAspect(@RequestBody SocialAspectRequest socialAspectRequest) {
        try {
            Optional<Student> studentData = studentRepository.findById(socialAspectRequest.getStudentId());
            if (!studentData.isPresent()) {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
            Student student = studentData.get();

            SocialAspect socialAspect = new SocialAspect();
            socialAspect.setStudent(student);
            socialAspect.setLivingWith(socialAspectRequest.getLivingWith());
            socialAspect.setRelationshipWithClassmates(socialAspectRequest.getRelationshipWithClassmates());
            socialAspect.setRelationshipWithTeachers(socialAspectRequest.getRelationshipWithTeachers());
            socialAspect.setRelationshipWithFamily(socialAspectRequest.getRelationshipWithFamily());
            socialAspectRepository.save(socialAspect);

            student.setSocialAspect(socialAspect);
            studentRepository.save(student);

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    */

    @GetMapping
    public ResponseEntity<List<SocialAspect>> getAllSocialAspects() {
        try {
            List<SocialAspect> socialAspects = socialAspectRepository.findAll();

            if (socialAspects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(socialAspects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialAspect> getSocialAspectById(@PathVariable("id") Long id) {
        Optional<SocialAspect> socialAspectData = socialAspectRepository.findById(id);
        return socialAspectData.map(socialAspect -> new ResponseEntity<>(socialAspect, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialAspect> updateSocialAspect(@PathVariable("id") Long id, @RequestBody SocialAspectRequest socialAspectRequest) {
        Optional<SocialAspect> socialAspectData = socialAspectRepository.findById(id);

        if (socialAspectData.isPresent()) {
            SocialAspect existingSocialAspect = socialAspectData.get();

            if (socialAspectRequest.getLivingWith() != null) {
                existingSocialAspect.setLivingWith(socialAspectRequest.getLivingWith());
            }
            if (socialAspectRequest.getRelationshipWithClassmates() != null) {
                existingSocialAspect.setRelationshipWithClassmates(socialAspectRequest.getRelationshipWithClassmates());
            }
            if (socialAspectRequest.getRelationshipWithTeachers() != null) {
                existingSocialAspect.setRelationshipWithTeachers(socialAspectRequest.getRelationshipWithTeachers());
            }
            if (socialAspectRequest.getRelationshipWithFamily() != null) {
                existingSocialAspect.setRelationshipWithFamily(socialAspectRequest.getRelationshipWithFamily());
            }

            SocialAspect updatedSocialAspect = socialAspectRepository.save(existingSocialAspect);
            return new ResponseEntity<>(updatedSocialAspect, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
