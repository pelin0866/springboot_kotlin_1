package org.example.mapper

import org.example.dto.CreateUserRequest
import org.example.dto.UpdateUserRequest
import org.example.dto.UserResponse
import org.example.model.User

// DTO -> Entity (create)
fun CreateUserRequest.toEntity(): User =
    User(name = this.name, email = this.email)

// Entity -> DTO (response)
fun User.toResponse(): UserResponse =
    UserResponse(
        id = requireNotNull(this.id) { "Persist edilmemiş User için id null olamaz" },
        name = this.name,
        email = this.email
    )

// Update DTO -> Entity (copy ile güncelle)
fun UpdateUserRequest.applyTo(entity: User): User =
    entity.copy(name = this.name, email = this.email)
