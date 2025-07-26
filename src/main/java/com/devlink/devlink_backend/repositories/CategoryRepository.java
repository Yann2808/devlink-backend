package com.devlink.devlink_backend.repositories;

import com.devlink.devlink_backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Dépôt JPA pour l'entité Category.
 * Fournit des méthodes CRUD et des méthodes de recherche personnalisées.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Méthode pour trouver une catégorie par son nom.
    Optional<Category> findByName(String name);
}
