package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.rooms.RoomContent;
import com.reglus.backend.model.entities.rooms.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RoomContentRepository extends JpaRepository<RoomContent, Long> {
    List<RoomContent> findByRoom(Room room);
}