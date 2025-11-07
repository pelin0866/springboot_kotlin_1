package org.example.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI().info(
            Info()
                .title("Kotlin JWT Blog API")
                .version("v1")
                .description("Users, Posts, Comments, Likes + JWT auth & role-based authorization")
        )
}