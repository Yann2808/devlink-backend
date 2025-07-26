package com.devlink.devlink_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) pour les requêtes d'authentification/inscription.
 * Contient les informations nécessaires (email, mot de passe, nom d'utilisateur)
 * envoyées par le client.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
    private String username; // Utilisé pour l'inscription
}
