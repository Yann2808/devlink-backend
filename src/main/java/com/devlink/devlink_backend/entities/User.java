package com.devlink.devlink_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entité JPA représentant un utilisateur dans la base de données.
 * Implémente UserDetails pour l'intégration avec Spring Security.
 */
@Data // Génère getters, setters, toString, equals, hashCode via Lombok
@Builder // Génère un builder pour faciliter la création d'instances
@NoArgsConstructor // Génère un constructeur sans arguments
@AllArgsConstructor // Génère un constructeur avec tous les arguments
@Entity // Marque cette classe comme une entité JPA
@Table(name = "users") // Spécifie le nom de la table dans la base de données
public class User implements UserDetails {

    @Id // Marque le champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Stratégie de génération automatique de l'ID
    private Long id;

    @Column(unique = true, nullable = false) // L'email doit être unique et non nul
    private String email;

    @Column(nullable = false) // Le mot de passe ne doit pas être nul
    private String password;

    @Column(nullable = false) // Le nom d'utilisateur ne doit pas être nul
    private String username;

    // TODO: Vous pouvez ajouter des rôles si nécessaire (ex: USER, ADMIN)
    // Pour l'instant, nous utiliserons un rôle par défaut simple.
    @Enumerated(EnumType.STRING) // Stocke l'énumération comme une chaîne de caractères
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retourne les autorités (rôles) de l'utilisateur.
        // Pour l'instant, un utilisateur aura un rôle basé sur l'énumération Role.
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // L'utilisateur n'expire jamais
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // L'utilisateur n'est jamais bloqué
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Les identifiants n'expirent jamais
    }

    @Override
    public boolean isEnabled() {
        return true; // L'utilisateur est toujours activé
    }

    // Énumération pour les rôles d'utilisateur
    public enum Role {
        USER,
        ADMIN // Pour des fonctionnalités bonus futures
    }

}
