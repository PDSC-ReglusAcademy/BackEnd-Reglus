package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.model.entities.users.login.LoginRequest;
import com.reglus.backend.repositories.UserRepository;
import com.reglus.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o PasswordEncoder

    @Autowired
    private JwtUtil jwtUtil; // Utilitário JWT injetado

    /**
     * Endpoint para login do usuário.
     * - Valida o e-mail e a senha.
     * - Gera um token JWT se a autenticação for bem-sucedida.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Busca o usuário pelo e-mail
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Verifica a senha usando o BCrypt
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
                // Gera o token JWT
                String token = jwtUtil.generateToken(user.getEmail());

                // Retorna a resposta com o token
                Map<String, Object> response = new HashMap<>();
                response.put("user", user);
                response.put("user_type", user.getUserType());
                response.put("token", token); // Adiciona o token na resposta
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
    }
}