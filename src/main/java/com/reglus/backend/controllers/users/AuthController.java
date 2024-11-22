package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.model.entities.users.login.LoginRequest;
import com.reglus.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")// Substitua pela URL correta
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Busca o usuário pelo e-mail
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Valida a senha diretamente
            if (loginRequest.getPassword().equals(user.getPasswordHash())) {
                // Crie um Map para retornar o tipo de usuário e outros dados
                Map<String, Object> response = new HashMap<>();
                response.put("user", user); // Inclui o objeto de usuário completo
                response.put("user_type", user.getUserType()); // Presumindo que o campo userType existe no modelo User

                // Retorna sucesso com detalhes do usuário
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
    }
}
