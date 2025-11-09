package org.example.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun openAPI(): io.swagger.v3.oas.models.OpenAPI =
        io.swagger.v3.oas.models.OpenAPI()
            .info(io.swagger.v3.oas.models.info.Info()
                .title("Demo API")
                .version("v1"))
            .components(io.swagger.v3.oas.models.Components().addSecuritySchemes(
                "bearer-jwt",
                io.swagger.v3.oas.models.security.SecurityScheme()
                    .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            ))
            .addSecurityItem(io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearer-jwt"))
}