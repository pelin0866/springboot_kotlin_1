package org.example.dto

import jakarta.validation.constraints.NotBlank

data class CreatePostRequest(
    @field:NotBlank val title: String,
    @field:NotBlank val content: String
)
data class PostResponse(
    val id: Long, //db nin urettigi benzersiz kimlik
    val title: String,
    val content: String
)
// UPDATE post: /posts/{postId}
data class UpdatePostRequest(val title: String, val content: String)
