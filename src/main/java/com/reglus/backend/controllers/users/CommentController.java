package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.rooms.Comment;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.RoomRepository;
import com.reglus.backend.repositories.CommentRepository;
import com.reglus.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms/{roomId}/comments")
public class CommentController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CommentRepository commentRepository;

    // Adiciona comentário público
    @PostMapping("/public")
    public ResponseEntity<Comment> addPublicComment(@PathVariable Long roomId, @RequestBody Comment comment) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isPresent()) {
            comment.setRoom(roomOptional.get());
            comment.setPublic(true);
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUpdatedAt(LocalDateTime.now());

            Comment savedComment = commentRepository.save(comment);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Adiciona comentário privado de um estudante
    @PostMapping("/private/{studentId}")
    public ResponseEntity<Comment> addPrivateComment(
            @PathVariable Long roomId, @PathVariable Long studentId, @RequestBody Comment comment) {

        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (roomOptional.isPresent() && studentOptional.isPresent()) {
            comment.setRoom(roomOptional.get());
            comment.setStudent(studentOptional.get());
            comment.setPublic(false);  // Indica que é um comentário privado
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUpdatedAt(LocalDateTime.now());

            Comment savedComment = commentRepository.save(comment);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Retorna todos os comentários públicos
    @GetMapping("/public")
    public ResponseEntity<List<Comment>> getPublicComments(@PathVariable Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isPresent()) {
            // Retorna todos os comentários públicos da sala
            List<Comment> comments = commentRepository.findByRoomAndIsPublic(roomOptional.get(), true);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Retorna todos os comentários privados de um estudante
    @GetMapping("/private/{studentId}")
    public ResponseEntity<List<Comment>> getPrivateComments(
            @PathVariable Long roomId, @PathVariable Long studentId) {

        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (roomOptional.isPresent() && studentOptional.isPresent()) {
            // Retorna todos os comentários privados do estudante na sala
            List<Comment> comments = commentRepository.findByRoomAndStudentAndIsPublic(
                    roomOptional.get(), studentOptional.get(), false);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Editar um comentário
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long roomId, @PathVariable Long commentId, @RequestBody Comment commentDetails) {

        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();

            // Verifica se o comentário pertence à sala especificada
            if (!comment.getRoom().getRoomId().equals(roomId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Comentário não pertence à sala
            }

            // Atualiza o conteúdo do comentário
            comment.setContent(commentDetails.getContent());
            comment.setUpdatedAt(LocalDateTime.now());

            Comment updatedComment = commentRepository.save(comment);
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Comentário não encontrado
    }

    // Deletar um comentário
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(
            @PathVariable Long roomId, @PathVariable Long commentId) {

        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();

            // Verifica se o comentário pertence à sala especificada
            if (!comment.getRoom().getRoomId().equals(roomId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Comentário não pertence à sala
            }

            // Remove o comentário
            commentRepository.delete(comment);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Comentário não encontrado
    }
}