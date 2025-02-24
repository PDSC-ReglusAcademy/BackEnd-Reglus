package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.rooms.RoomContent;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.repositories.RoomRepository;
import com.reglus.backend.repositories.RoomContentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms/{roomId}/contents")
public class RoomContentController {

    private static final Logger logger = LoggerFactory.getLogger(RoomContentController.class);
    private static final Set<String> ALLOWED_TYPES = Set.of("text", "image", "video", "link");

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomContentRepository roomContentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    @Transactional
    public ResponseEntity<?> addContent(
            @PathVariable Long roomId,
            @RequestParam String type,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) MultipartFile file) {

        // Verifica se a sala existe
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isEmpty()) {
            return errorResponse("Sala com ID " + roomId + " não encontrada.", HttpStatus.NOT_FOUND);
        }

        // Valida o tipo de conteúdo
        if (!ALLOWED_TYPES.contains(type)) {
            return errorResponse("Tipo inválido. Permitidos: text, image, video, link.", HttpStatus.BAD_REQUEST);
        }

        // Cria o objeto RoomContent
        RoomContent roomContent = new RoomContent();
        roomContent.setRoom(roomOptional.get());
        roomContent.setType(type);
        roomContent.setCreatedAt(LocalDateTime.now());
        roomContent.setUpdatedAt(LocalDateTime.now());

        try {
            switch (type) {
                case "text":
                    if (content == null || content.isBlank()) {
                        return errorResponse("O conteúdo de texto não pode estar vazio.", HttpStatus.BAD_REQUEST);
                    }
                    roomContent.setContent(content);
                    break;

                case "image":
                case "video":
                    if (file == null || file.isEmpty()) {
                        return errorResponse("Arquivo de " + type + " não enviado.", HttpStatus.BAD_REQUEST);
                    }
                    roomContent.setFileData(file.getBytes()); // Salva o arquivo como BLOB
                    break;

                case "link":
                    if (content == null || content.isBlank()) {
                        return errorResponse("O link não pode estar vazio.", HttpStatus.BAD_REQUEST);
                    }
                    roomContent.setContent(content);
                    break;
            }

            // Salva o conteúdo no banco de dados
            RoomContent savedContent = roomContentRepository.save(roomContent);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedContent);

        } catch (IOException e) {
            logger.error("Erro ao salvar arquivo", e);
            return errorResponse("Erro interno ao salvar o arquivo.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Erro ao salvar conteúdo no banco", e);
            return errorResponse("Erro ao salvar conteúdo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllContents(@PathVariable Long roomId) {
        // Verifica se a sala existe
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se a sala não existir
        }

        // Busca todos os conteúdos associados à sala
        List<RoomContent> contents = roomContentRepository.findByRoom(roomOptional.get());

        // Filtra e mapeia os conteúdos para o formato adequado
        List<Object> response = contents.stream()
                .map(content -> {
                    if (content.getType().equals("text") || content.getType().equals("link")) {
                        // Retorna apenas os campos relevantes para text e link
                        return Map.of(
                                "id", content.getId() != null ? content.getId() : 0L, // Evita null
                                "type", content.getType() != null ? content.getType() : "", // Evita null
                                "content", content.getContent() != null ? content.getContent() : "", // Evita null
                                "createdAt", content.getCreatedAt() != null ? content.getCreatedAt() : LocalDateTime.now(), // Evita null
                                "updatedAt", content.getUpdatedAt() != null ? content.getUpdatedAt() : LocalDateTime.now() // Evita null
                        );
                    } else if (content.getType().equals("image") || content.getType().equals("video")) {
                        // Retorna um link para exibir o arquivo
                        return Map.of(
                                "id", content.getId() != null ? content.getId() : 0L, // Evita null
                                "type", content.getType() != null ? content.getType() : "", // Evita null
                                "displayUrl", "/api/rooms/" + roomId + "/contents/" + content.getId() + "/display",
                                "createdAt", content.getCreatedAt() != null ? content.getCreatedAt() : LocalDateTime.now(), // Evita null
                                "updatedAt", content.getUpdatedAt() != null ? content.getUpdatedAt() : LocalDateTime.now() // Evita null
                        );
                    }
                    return null; // Nunca deve acontecer, pois os tipos são validados no POST
                })
                .filter(Objects::nonNull) // Remove possíveis valores nulos
                .collect(Collectors.toList());

        return ResponseEntity.ok(response); // Retorna 200 com a lista de conteúdos
    }

    @GetMapping("/{contentId}/display")
    public ResponseEntity<ByteArrayResource> displayFile(@PathVariable Long contentId) {
        Optional<RoomContent> contentOptional = roomContentRepository.findById(contentId);
        if (contentOptional.isEmpty() || contentOptional.get().getFileData() == null) {
            return ResponseEntity.notFound().build();
        }

        RoomContent content = contentOptional.get();
        ByteArrayResource resource = new ByteArrayResource(content.getFileData());

        // Define o tipo de mídia com base no tipo de conteúdo
        MediaType mediaType;
        if (content.getType().equals("image")) {
            mediaType = MediaType.IMAGE_JPEG; // Ou MediaType.IMAGE_PNG, dependendo do formato
        } else if (content.getType().equals("video")) {
            mediaType = MediaType.valueOf("video/mp4"); // Ajuste conforme o formato do vídeo
        } else {
            return ResponseEntity.badRequest().build(); // Tipo de arquivo não suportado para exibição
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }

    @PutMapping("/{contentId}")
    @Transactional
    public ResponseEntity<?> updateContent(
            @PathVariable Long roomId,
            @PathVariable Long contentId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) MultipartFile file) {

        // Verifica se a sala existe
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isEmpty()) {
            return errorResponse("Sala com ID " + roomId + " não encontrada.", HttpStatus.NOT_FOUND);
        }

        // Verifica se o conteúdo existe e pertence à sala especificada
        Optional<RoomContent> contentOptional = roomContentRepository.findById(contentId);
        if (contentOptional.isEmpty()) {
            return errorResponse("Conteúdo com ID " + contentId + " não encontrado.", HttpStatus.NOT_FOUND);
        }

        RoomContent roomContent = contentOptional.get();
        if (!roomContent.getRoom().getRoomId().equals(roomId)) {
            return errorResponse("Conteúdo não pertence à sala especificada.", HttpStatus.BAD_REQUEST);
        }

        // Atualiza o tipo, se fornecido
        if (type != null && !type.isBlank()) {
            if (!ALLOWED_TYPES.contains(type)) {
                return errorResponse("Tipo inválido. Permitidos: text, image, video, link.", HttpStatus.BAD_REQUEST);
            }
            roomContent.setType(type);
        }

        // Atualiza o conteúdo ou o arquivo, dependendo do tipo
        try {
            switch (roomContent.getType()) {
                case "text":
                case "link":
                    if (content == null || content.isBlank()) {
                        return errorResponse("O conteúdo de " + roomContent.getType() + " não pode estar vazio.", HttpStatus.BAD_REQUEST);
                    }
                    roomContent.setContent(content);
                    break;

                case "image":
                case "video":
                    if (file != null && !file.isEmpty()) {
                        roomContent.setFileData(file.getBytes()); // Atualiza o arquivo
                    } else if (content != null && !content.isBlank()) {
                        return errorResponse("Para tipos de arquivo, envie um arquivo válido.", HttpStatus.BAD_REQUEST);
                    }
                    break;
            }

            // Atualiza a data de modificação
            roomContent.setUpdatedAt(LocalDateTime.now());

            // Salva as alterações no banco de dados
            RoomContent updatedContent = roomContentRepository.save(roomContent);
            return ResponseEntity.ok(updatedContent); // Retorna 200 com o conteúdo atualizado

        } catch (IOException e) {
            logger.error("Erro ao atualizar arquivo", e);
            return errorResponse("Erro interno ao atualizar o arquivo.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Erro ao atualizar conteúdo", e);
            return errorResponse("Erro ao atualizar conteúdo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{contentId}")
    @Transactional
    public ResponseEntity<?> deleteContent(@PathVariable Long roomId, @PathVariable Long contentId) {
        // Verifica se a sala existe
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isEmpty()) {
            return errorResponse("Sala com ID " + roomId + " não encontrada.", HttpStatus.NOT_FOUND);
        }

        // Verifica se o conteúdo existe e pertence à sala especificada
        Optional<RoomContent> contentOptional = roomContentRepository.findById(contentId);
        if (contentOptional.isEmpty()) {
            return errorResponse("Conteúdo com ID " + contentId + " não encontrado.", HttpStatus.NOT_FOUND);
        }

        RoomContent content = contentOptional.get();
        if (!content.getRoom().getRoomId().equals(roomId)) {
            return errorResponse("Conteúdo não pertence à sala especificada.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Remove o conteúdo do banco de dados
            roomContentRepository.delete(content);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (Exception e) {
            logger.error("Erro ao excluir conteúdo", e);
            return errorResponse("Erro ao excluir conteúdo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para retornar respostas de erro
    private ResponseEntity<String> errorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(message);
    }
}