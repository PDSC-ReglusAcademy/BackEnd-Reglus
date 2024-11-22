package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.model.entities.users.StudentRequest;
import com.reglus.backend.model.entities.users.smf.*;
import com.reglus.backend.repositories.SocialAspectRepository;
import com.reglus.backend.model.enums.UserType;
import com.reglus.backend.repositories.StudentRepository;
import com.reglus.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SocialAspectRepository socialAspectRepository;

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest studentRequest) {
        try {
            User user = new User();
            user.setUserType(UserType.STUDENT);
            user.setEmail(studentRequest.getEmail());
            user.setPasswordHash(studentRequest.getPasswordHash());
            user.setName(studentRequest.getName());
            user.setDateBirth(studentRequest.getDateBirth());
            user.setGender(studentRequest.getGender());
            user.setDisability(studentRequest.getDisability());
            user.setEducationLevel(studentRequest.getEducationLevel());
            user.setInstituteName(studentRequest.getInstituteName());
            userRepository.save(user);

            Student student = new Student();
            student.setUser(user);
            student.setState(studentRequest.getState());
            student.setCity(studentRequest.getCity());
            student.setFinalObservations(studentRequest.getFinalObservations());

            SocialAspect socialAspect = new SocialAspect();
            socialAspect.setLivingWith(studentRequest.getSocialAspectRequest().getLivingWith());
            socialAspect.setRelationshipWithClassmates(studentRequest.getSocialAspectRequest().getRelationshipWithClassmates());
            socialAspect.setRelationshipWithTeachers(studentRequest.getSocialAspectRequest().getRelationshipWithTeachers());
            socialAspect.setRelationshipWithFamily(studentRequest.getSocialAspectRequest().getRelationshipWithFamily());
            socialAspect.setStudent(student);
            student.setSocialAspect(socialAspect);

            StudyHabit studyHabit = new StudyHabit();
            studyHabit.setStudyMethods(studentRequest.getStudyHabitRequest().getStudyMethods());
            studyHabit.setStudyHoursPerDay(studentRequest.getStudyHabitRequest().getStudyHoursPerDay());
            studyHabit.setStudyLocations(studentRequest.getStudyHabitRequest().getStudyLocations());
            studyHabit.setStudyPlan(studentRequest.getStudyHabitRequest().getStudyPlan());
            studyHabit.setStudent(student);
            student.setStudyHabit(studyHabit);

            HealthWellbeing healthWellbeing = new HealthWellbeing();
            healthWellbeing.setHealthCondition(studentRequest.getHealthWellbeingRequest().getHealthCondition());
            healthWellbeing.setPhysicalActivity(studentRequest.getHealthWellbeingRequest().getPhysicalActivity());
            healthWellbeing.setDietaryEvaluation(studentRequest.getHealthWellbeingRequest().getDietaryEvaluation());
            healthWellbeing.setSleepHours(studentRequest.getHealthWellbeingRequest().getSleepHours());
            healthWellbeing.setStudent(student);
            student.setHealthWellbeing(healthWellbeing);

            InterestHobby interestHobby = new InterestHobby();
            interestHobby.setActivitiesOutsideSchool(studentRequest.getInterestHobbyRequest().getActivitiesOutsideSchool());
            interestHobby.setDreamsGoals(studentRequest.getInterestHobbyRequest().getDreamsGoals());
            interestHobby.setStudent(student);
            student.setInterestHobby(interestHobby);

            SelfAssessment selfAssessment = new SelfAssessment();
            selfAssessment.setPerformanceEvaluation(studentRequest.getSelfAssessmentRequest().getPerformanceEvaluation());
            selfAssessment.setStrengths(studentRequest.getSelfAssessmentRequest().getStrengths());
            selfAssessment.setImprovementAreas(studentRequest.getSelfAssessmentRequest().getImprovementAreas());
            selfAssessment.setStudent(student);
            student.setSelfAssessment(selfAssessment);

            studentRepository.save(student);

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Optional<Student> studentData = studentRepository.findById(id);

        if (studentData.isPresent()) {
            return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") Long id) {
        try {
            Optional<Student> studentData = studentRepository.findById(id);

            if (studentData.isPresent()) {
                Student student = studentData.get();
                User user = student.getUser();

                studentRepository.deleteById(id);
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
