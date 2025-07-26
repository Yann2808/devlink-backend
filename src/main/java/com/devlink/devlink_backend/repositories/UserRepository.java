package com.devlink.devlink_backend.repositories;

import com.devlink.devlink_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Dépôt JPA pour l'entité User.
 * Fournit des méthodes CRUD et des méthodes de recherche personnalisées.
 */
@Repository // Marque cette interface comme un composant de dépôt Spring
public interface UserRepository extends JpaRepository<User, Long> {

    // Méthode pour trouver un utilisateur par son email. Utilisée pour l'authentification.
    Optional<User> findByEmail(String email);
}
