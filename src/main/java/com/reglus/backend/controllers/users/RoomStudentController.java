package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.rooms.RoomStudent;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.RoomRepository;
import com.reglus.backend.repositories.RoomStudentRepository;
import com.reglus.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms/{roomId}/students")
public class RoomStudentController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomStudentRepository roomStudentRepository;

    // Adicionar um estudante à sala
    @PostMapping("/{studentId}")
    public ResponseEntity<RoomStudent> addStudentToRoom(
            @PathVariable Long roomId, @PathVariable Long studentId) {

        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (roomOptional.isPresent() && studentOptional.isPresent()) {
            Room room = roomOptional.get();
            Student student = studentOptional.get();

            // Verifica se o estudante já está na sala
            Optional<RoomStudent> existingRoomStudent = roomStudentRepository.findByRoomAndStudent(room, student);
            if (existingRoomStudent.isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT); // Estudante já está na sala
            }

            // Cria uma nova relação entre a sala e o estudante
            RoomStudent roomStudent = new RoomStudent();
            roomStudent.setRoom(room);
            roomStudent.setStudent(student);
            roomStudent.setEnrollmentDate(LocalDate.now());
            roomStudent.setCompletionStatus("Enrolled");

            RoomStudent savedRoomStudent = roomStudentRepository.save(roomStudent);
            return new ResponseEntity<>(savedRoomStudent, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Sala ou estudante não encontrado
        }
    }

    // Remover um estudante da sala
    @DeleteMapping("/{studentId}")
    public ResponseEntity<HttpStatus> removeStudentFromRoom(
            @PathVariable Long roomId, @PathVariable Long studentId) {

        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (roomOptional.isPresent() && studentOptional.isPresent()) {
            Room room = roomOptional.get();
            Student student = studentOptional.get();

            // Busca a relação entre a sala e o estudante
            Optional<RoomStudent> roomStudentOptional = roomStudentRepository.findByRoomAndStudent(room, student);
            if (roomStudentOptional.isPresent()) {
                roomStudentRepository.delete(roomStudentOptional.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Estudante removido com sucesso
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Estudante não está na sala
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Sala ou estudante não encontrado
        }
    }
}