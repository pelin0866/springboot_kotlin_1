package org.example.dto

import jakarta.validation.constraints.*

data class RegisterRequest(
    @field:NotBlank val name: String,
    @field:Email val email: String,
    @field:Size(min = 6) val password: String
)

data class LoginRequest(
    @field:Email val email: String,
    @field:NotBlank val password: String
)

data class AuthResponse(val token: String)
