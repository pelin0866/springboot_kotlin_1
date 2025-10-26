package org.example.mapper

import org.example.dto.CommentResponse
import org.example.dto.CreateCommentRequest
import org.example.model.Comment
import org.example.model.Post
import org.example.model.User


fun CreateCommentRequest.toEntity(post: Post)= Comment(
    comment= this.comment, //commentEntityi= CreateCommentRequest DTO dan gelen comment e
    post = post
)


fun Comment.toResponse()= CommentResponse(
    id= requireNotNull(this.id),
    comment= this.comment
)