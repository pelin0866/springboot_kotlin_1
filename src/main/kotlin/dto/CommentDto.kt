package org.example.dto

import jakarta.validation.constraints.NotBlank

data class CreateCommentRequest(
    @field:NotBlank val comment: String
)

data class CommentResponse(
    val id: Long, //id from assigned by db
    val comment: String
)

data class UpdateCommentRequest(
    @field:NotBlank val comment: String
)