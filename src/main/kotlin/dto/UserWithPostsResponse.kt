package org.example.dto

data class UserWithPostsResponse(
    val id: Long,
    val name: String,
    val email: String,
    val posts: List<PostSummary>
)

data class PostSummary(
    val id:Long,
    val title: String
)
