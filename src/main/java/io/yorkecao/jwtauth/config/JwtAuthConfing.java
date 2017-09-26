package io.yorkecao.jwtauth.config;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yorke
 */
@Configuration
@ConfigurationProperties(prefix = "jwt-auth")
public class JwtAuthConfing {
    @NotBlank private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
