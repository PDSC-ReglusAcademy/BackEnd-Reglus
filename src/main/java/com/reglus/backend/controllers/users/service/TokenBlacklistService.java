//package com.reglus.backend.controllers.users.service;
//
//import org.springframework.stereotype.Service;
//
//import com.reglus.backend.repositories.TokenBlacklistRepository;  // Repositório para persistência
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.util.Optional;
//
//@Service
//public class TokenBlacklistService {
//
//    @Autowired
//    private TokenBlacklistRepository tokenBlacklistRepository;
//
//    // Adiciona o token à blacklist com a data de expiração
//    public void addToBlacklist(String token) {
//        TokenBlacklist tokenBlacklist = new TokenBlacklist(token, Instant.now());
//        tokenBlacklistRepository.save(tokenBlacklist);
//    }
//
//    // Verifica se o token está na blacklist (verifica também se o token não expirou)
//    public boolean isTokenBlacklisted(String token) {
//        Optional<TokenBlacklist> blacklistedToken = tokenBlacklistRepository.findByToken(token);
//        return blacklistedToken.isPresent() && !isTokenExpired(blacklistedToken.get());
//    }
//
//    // Limpa a blacklist (pode ser chamado por admins ou ao limpar tokens expirados)
//    public void clearBlacklist() {
//        tokenBlacklistRepository.deleteAll();
//    }
//
//    // Verifica se o token expirou
//    private boolean isTokenExpired(TokenBlacklist tokenBlacklist) {
//        // Aqui, definimos que o token expira após 1 hora (3600 segundos)
//        return tokenBlacklist.getTimestamp().plusSeconds(3600).isBefore(Instant.now());
//    }
//}
//
