package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.rooms.RoomStudent;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomStudentRepository extends JpaRepository<RoomStudent, Long> {
    List<RoomStudent> findByRoom(Room room);
    Optional<RoomStudent> findByRoomAndStudent(Room room, Student student);

}