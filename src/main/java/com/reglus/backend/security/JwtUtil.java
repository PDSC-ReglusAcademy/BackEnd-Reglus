package com.reglus.backend.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Chave secreta usada para assinar os tokens
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Gera um token JWT para o usuário especificado.
     *
     * @param username Nome do usuário para quem o token é gerado.
     * @return Token JWT gerado.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(); // Claims adicionais (vazio neste exemplo)
        return Jwts.builder()
                .setClaims(claims) // Define os claims do token
                .setSubject(username) // Define o usuário associado ao token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expira em 10 horas
                .signWith(key) // Assina o token com a chave secreta
                .compact(); // Constrói o token
    }

    /**
     * Extrai o nome do usuário (subject) do token JWT.
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token) // Analisa o token
                .getBody().getSubject(); // Retorna o subject
    }

    /**
     * Verifica se o token é válido para o usuário fornecido.
     */
    public boolean isTokenValid(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    /**
     * Verifica se o token está expirado.
     */
    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody().getExpiration().before(new Date()); // Compara a data de expiração com a data atual
    }
}
