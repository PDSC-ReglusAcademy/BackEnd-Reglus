package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.rooms.RoomStudent;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.RoomRepository;
import com.reglus.backend.repositories.RoomStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private RoomStudentRepository roomStudentRepository;

    // Criar uma nova sala
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());
        Room savedRoom = roomRepository.save(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    // Obter todas as salas
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    // Obter uma sala por ID
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Buscar salas por educador
    @GetMapping("/educator/{educatorId}")
    public ResponseEntity<List<Room>> getRoomsByEducator(@PathVariable Long educatorId) {
        List<Room> rooms = roomRepository.findByEducator_EducatorId(educatorId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    // Obter todos os estudantes de uma sala
    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudentsByRoom(@PathVariable Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            List<RoomStudent> roomStudents = roomStudentRepository.findByRoom(room);
            List<Student> students = roomStudents.stream()
                    .map(RoomStudent::getStudent)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Atualizar uma sala
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.setName(roomDetails.getName());
            room.setDescription(roomDetails.getDescription());
            room.setStartDate(roomDetails.getStartDate());
            room.setEndDate(roomDetails.getEndDate());
            room.setUpdatedAt(LocalDateTime.now());

            Room updatedRoom = roomRepository.save(room);
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Excluir uma sala
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRoom(@PathVariable Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent()) {
            roomRepository.delete(roomOptional.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}