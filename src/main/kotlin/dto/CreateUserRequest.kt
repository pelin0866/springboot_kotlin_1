package org.example.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank(message = "name boş olamaz")
    val name: String,

    @field:NotBlank(message = "email boş olamaz")
    @field:Email(message = "email formatı geçersiz")
    val email: String
)
