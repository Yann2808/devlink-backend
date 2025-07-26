package com.devlink.devlink_backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration des propriétés JWT lues depuis application.properties.
 * Permet d'injecter la clé secrète et le temps d'expiration des tokens JWT.
 */
@Configuration
@ConfigurationProperties(prefix = "jwt") // Indique que les propriétés préfixées par "jwt" seront liées à cette classe
@Getter // Génère automatiquement les getters via Lombok
@Setter // Génère automatiquement les setters via Lombok
public class JwtConfig {

    private String secret; // Correspond à jwt.secret dans application.properties
    private long expiration; // Correspond à jwt.expiration dans application.properties

}
