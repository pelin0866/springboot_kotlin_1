package org.example.dto

data class PostWithCommentsResponse(
    val id: Long,
    val title: String,
    val content: String,
    val comments: List<CommentSummary>
)

data class CommentSummary(
    val id: Long,
    val comment: String
)
