package com.devlink.devlink_backend.dtos;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO (Data Transfer Object) pour les réponses d'authentification.
 * Contient le token JWT généré après une connexion ou inscription réussie.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
