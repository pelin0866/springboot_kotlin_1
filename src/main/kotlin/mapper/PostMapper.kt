package org.example.mapper

import org.example.dto.CreatePostRequest
import org.example.dto.PostResponse
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


