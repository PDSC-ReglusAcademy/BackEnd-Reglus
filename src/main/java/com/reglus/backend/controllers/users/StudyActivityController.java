package com.reglus.backend.controllers.users;


import com.reglus.backend.model.entities.rooms.StudyActivity;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.repositories.StudyActivityRepository;
import com.reglus.backend.repositories.StudentRepository;
import com.reglus.backend.repositories.RoomRepository; //Repositorio da classe entidy Room ainda não implementado
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/study-activities")
@CrossOrigin(origins = "*")
public class StudyActivityController {

    @Autowired
    private StudyActivityRepository studyActivityRepository;

    @Autowired
    private StudentRepository studentRepository;


    //Repositorio da classe entidy Room ainda não implementado
    @Autowired
    private RoomRepository roomRepository;

    // Criar uma nova sessão de estudo
    @PostMapping
    public ResponseEntity<?> createStudyActivity(@RequestBody StudyActivity studyActivity) {
        try {
            StudyActivity savedStudyActivity = studyActivityRepository.save(studyActivity);
            return new ResponseEntity<>(savedStudyActivity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Obter todas as sessões de estudo
    @GetMapping
    public ResponseEntity<List<StudyActivity>> getAllStudyActivities() {
        List<StudyActivity> studyActivities = studyActivityRepository.findAll();
        return new ResponseEntity<>(studyActivities, HttpStatus.OK);
    }

    // Obter uma sessão de estudo por ID
// Obter uma sessão de estudo por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudyActivityById(@PathVariable Long id) {
        Optional<StudyActivity> studyActivity = studyActivityRepository.findById(id);
        if (studyActivity.isPresent()) {
            return new ResponseEntity<>(studyActivity.get(), HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Sessão de estudo não encontrada");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    // Atualizar uma sessão de estudo
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudyActivity(@PathVariable Long id, @RequestBody StudyActivity updatedStudyActivity) {
        try {
            StudyActivity studyActivity = studyActivityRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sessão de estudo não encontrada"));

            studyActivity.setDurationHours(updatedStudyActivity.getDurationHours());
            studyActivity.setPointsEarned(updatedStudyActivity.getPointsEarned());

            StudyActivity savedStudyActivity = studyActivityRepository.save(studyActivity);
            return new ResponseEntity<>(savedStudyActivity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Deletar uma sessão de estudo
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudyActivity(@PathVariable Long id) {
        try {
            if (!studyActivityRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            studyActivityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obter sessões de estudo por estudante (Student)
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudyActivity>> getStudyActivitiesByStudent(@PathVariable Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.map(value -> new ResponseEntity<>(studyActivityRepository.findByStudent(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obter sessões de estudo por sala (Room)
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<StudyActivity>> getStudyActivitiesByRoom(@PathVariable Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        return room.map(value -> new ResponseEntity<>(studyActivityRepository.findByRoom(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obter sessões de estudo por data
    @GetMapping("/date/{activityDate}")
    public ResponseEntity<List<StudyActivity>> getStudyActivitiesByDate(@PathVariable LocalDate activityDate) {
        List<StudyActivity> studyActivities = studyActivityRepository.findByActivityDate(activityDate);
        return new ResponseEntity<>(studyActivities, HttpStatus.OK);
    }

    // Obter sessões de estudo por estudante e data
    @GetMapping("/student/{studentId}/date/{activityDate}")
    public ResponseEntity<List<StudyActivity>> getStudyActivitiesByStudentAndDate(@PathVariable Long studentId, @PathVariable LocalDate activityDate) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            List<StudyActivity> activities = studyActivityRepository.findByStudentAndActivityDate(student.get(), activityDate);
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obter sessões de estudo por sala e data
    @GetMapping("/room/{roomId}/date/{activityDate}")
    public ResponseEntity<List<StudyActivity>> getStudyActivitiesByRoomAndDate(@PathVariable Long roomId, @PathVariable LocalDate activityDate) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()) {
            List<StudyActivity> activities = studyActivityRepository.findByRoomAndActivityDate(room.get(), activityDate);
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obter sessões de estudo por estudante, sala e data
    @GetMapping("/student/{studentId}/room/{roomId}/date/{activityDate}")
    public ResponseEntity<List<StudyActivity>> getStudyActivitiesByStudentRoomAndDate(@PathVariable Long studentId, @PathVariable Long roomId, @PathVariable LocalDate activityDate) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Room> room = roomRepository.findById(roomId);

        if (student.isPresent() && room.isPresent()) {
            List<StudyActivity> activities = studyActivityRepository.findByStudentAndRoomAndActivityDate(student.get(), room.get(), activityDate);
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
