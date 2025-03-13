package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByEducator_EducatorId(Long educatorId);

}