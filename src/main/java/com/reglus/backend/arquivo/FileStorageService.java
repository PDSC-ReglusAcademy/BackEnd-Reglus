package com.reglus.backend.arquivo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get("./uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Não foi possível criar o diretório de upload.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            // Gera um nome único para o arquivo
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Salva o arquivo no diretório de upload
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Falha ao armazenar o arquivo.", ex);
        }
    }
}