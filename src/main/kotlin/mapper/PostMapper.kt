package org.example.mapper

import org.example.dto.*
import org.example.model.Post
import org.example.model.User

fun CreatePostRequest.toEntity(user: User) = Post(
    title = this.title,
    content = this.content,
    user= user
)

fun Post.toResponse()= PostResponse(
    id= requireNotNull(this.id),
    title= this.title,
    content= this.content
)

fun UpdatePostRequest.applyTo(entity: Post): Post =
    entity.copy(title = this.title, content = this.content)

fun Post.toWithCommentsResponse() = PostWithCommentsResponse(
    id = requireNotNull(this.id),
    title = this.title,
    content = this.content,
    comments = this.comments.map { c ->
        CommentSummary(
            id = requireNotNull(c.id),
            comment = c.comment
        )
    }
)


