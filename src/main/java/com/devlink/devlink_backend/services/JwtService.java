package com.devlink.devlink_backend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.devlink.devlink_backend.config.JwtConfig;

import javax.crypto.SecretKey; // Changement : utiliser SecretKey au lieu de Key
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service pour la génération et la validation des tokens JWT.
 */
@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    // Extrait le nom d'utilisateur (sujet) du token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrait une revendication spécifique du token JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Génère un token JWT pour un UserDetails
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Génère un token JWT avec des revendications supplémentaires
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extraClaims) // Changement : .claims() au lieu de .setClaims()
                .subject(userDetails.getUsername()) // Changement : .subject() au lieu de .setSubject()
                .issuedAt(new Date(System.currentTimeMillis())) // Changement : .issuedAt() au lieu de .setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration())) // Changement : .expiration() au lieu de .setExpiration()
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Reste identique
                .compact();
    }

    // Valide un token JWT
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Vérifie si le token est expiré
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extrait la date d'expiration du token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrait toutes les revendications du token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser() // Changement : .parser() au lieu de .parserBuilder()
                .verifyWith(getSigningKey()) // Changement : .verifyWith() au lieu de .setSigningKey()
                .build()
                .parseSignedClaims(token) // Changement : .parseSignedClaims() au lieu de .parseClaimsJws()
                .getPayload(); // Changement : .getPayload() au lieu de .getBody()
    }

    // Récupère la clé de signature à partir de la clé secrète JWT
    private SecretKey getSigningKey() { // Changement : SecretKey au lieu de Key
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}