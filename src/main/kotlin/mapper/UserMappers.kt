package org.example.mapper

import org.example.dto.*
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

fun UpdateUserRequest.applyTo(entity: User): User =
    entity.copy(name = this.name, email = this.email)

fun User.toWithPostsResponse() = UserWithPostsResponse(
    id= requireNotNull(this.id),
    name = this.name,
    email = this.email,
    posts = this.posts.map { PostSummary(id= requireNotNull(it.id), title = it.title) }
)