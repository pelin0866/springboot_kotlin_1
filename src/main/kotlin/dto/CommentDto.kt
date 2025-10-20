package org.example.dto

import jakarta.validation.constraints.NotBlank

data class CreateCommentRequest(
    @field:NotBlank val comment: String //burada user da gerekmiyor mu mesela
)

data class CommentResponse(
    val id: Long,//db nin kendi atadigi id
    val comment: String
)

data class UpdateCommentRequest(
    val comment: String
)