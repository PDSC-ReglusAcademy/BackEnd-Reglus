package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.schedule.Schedule;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.model.entities.users.StudentRequest;
import com.reglus.backend.model.entities.users.smf.*;
import com.reglus.backend.repositories.ScheduleRepository;
import com.reglus.backend.repositories.SocialAspectRepository;
import com.reglus.backend.model.enums.UserType;
import com.reglus.backend.repositories.StudentRepository;
import com.reglus.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o PasswordEncoder

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest studentRequest) {
        try {
            User user = new User();
            user.setUserType(UserType.STUDENT);
            user.setEmail(studentRequest.getEmail());
            user.setPasswordHash(passwordEncoder.encode(studentRequest.getPasswordHash()));
            user.setName(studentRequest.getName());
            user.setDateBirth(studentRequest.getDateBirth());
            user.setGender(studentRequest.getGender());
            user.setDisability(studentRequest.getDisability());
            user.setEducationLevel(studentRequest.getEducationLevel());
            user.setInstituteName(studentRequest.getInstituteName());
            user.setCity(studentRequest.getCity());
            user.setState(studentRequest.getState());
            userRepository.save(user);

            Student student = new Student();
            student.setUser(user);
            user.setState(studentRequest.getState());
            user.setCity(studentRequest.getCity());
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, @RequestBody StudentRequest studentRequest) {
        try {
            Optional<Student> studentData = studentRepository.findById(id);

            if (studentData.isPresent()) {
                Student student = studentData.get();
                User user = student.getUser();

                user.setEmail(studentRequest.getEmail());
                user.setName(studentRequest.getName());
                user.setDateBirth(studentRequest.getDateBirth());
                user.setGender(studentRequest.getGender());
                user.setDisability(studentRequest.getDisability());
                user.setEducationLevel(studentRequest.getEducationLevel());
                user.setInstituteName(studentRequest.getInstituteName());
                user.setCity(studentRequest.getCity());
                user.setState(studentRequest.getState());
                userRepository.save(user);

                student.setFinalObservations(studentRequest.getFinalObservations());

                SocialAspect socialAspect = student.getSocialAspect();
                socialAspect.setLivingWith(studentRequest.getSocialAspectRequest().getLivingWith());
                socialAspect.setRelationshipWithClassmates(studentRequest.getSocialAspectRequest().getRelationshipWithClassmates());
                socialAspect.setRelationshipWithTeachers(studentRequest.getSocialAspectRequest().getRelationshipWithTeachers());
                socialAspect.setRelationshipWithFamily(studentRequest.getSocialAspectRequest().getRelationshipWithFamily());
                socialAspectRepository.save(socialAspect);

                StudyHabit studyHabit = student.getStudyHabit();
                studyHabit.setStudyMethods(studentRequest.getStudyHabitRequest().getStudyMethods());
                studyHabit.setStudyHoursPerDay(studentRequest.getStudyHabitRequest().getStudyHoursPerDay());
                studyHabit.setStudyLocations(studentRequest.getStudyHabitRequest().getStudyLocations());
                studyHabit.setStudyPlan(studentRequest.getStudyHabitRequest().getStudyPlan());

                HealthWellbeing healthWellbeing = student.getHealthWellbeing();
                healthWellbeing.setHealthCondition(studentRequest.getHealthWellbeingRequest().getHealthCondition());
                healthWellbeing.setPhysicalActivity(studentRequest.getHealthWellbeingRequest().getPhysicalActivity());
                healthWellbeing.setDietaryEvaluation(studentRequest.getHealthWellbeingRequest().getDietaryEvaluation());
                healthWellbeing.setSleepHours(studentRequest.getHealthWellbeingRequest().getSleepHours());

                InterestHobby interestHobby = student.getInterestHobby();
                interestHobby.setActivitiesOutsideSchool(studentRequest.getInterestHobbyRequest().getActivitiesOutsideSchool());
                interestHobby.setDreamsGoals(studentRequest.getInterestHobbyRequest().getDreamsGoals());

                SelfAssessment selfAssessment = student.getSelfAssessment();
                selfAssessment.setPerformanceEvaluation(studentRequest.getSelfAssessmentRequest().getPerformanceEvaluation());
                selfAssessment.setStrengths(studentRequest.getSelfAssessmentRequest().getStrengths());
                selfAssessment.setImprovementAreas(studentRequest.getSelfAssessmentRequest().getImprovementAreas());

                studentRepository.save(student);

                return new ResponseEntity<>(student, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Student not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
            e.printStackTrace(); // Log da exceção
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}