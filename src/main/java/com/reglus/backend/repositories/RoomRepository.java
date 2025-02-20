package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}