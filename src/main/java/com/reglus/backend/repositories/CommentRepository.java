package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.rooms.Comment;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Retorna todos os comentários públicos de uma sala
    List<Comment> findByRoomAndIsPublic(Room room, boolean isPublic);

    // Retorna todos os comentários privados de um estudante em uma sala
    List<Comment> findByRoomAndStudentAndIsPublic(Room room, Student student, boolean isPublic);
}
