package org.example.mapper

import org.example.dto.CommentResponse
import org.example.dto.CreateCommentRequest
import org.example.model.Comment
import org.example.model.Post
import org.example.model.User

//CreateCommentRequest’te user ancak “yorumu kimin yazdığını da tutacağız” dersen gerekir.
//Bizim mevcut modelde yorum “hangi posta ait?” bilgisi önemli ve onu path’ten alıyoruz: POST /posts/{postId}/comments.
fun CreateCommentRequest.toEntity(post: Post)= Comment(
    comment= this.comment, //commentEntityi= CreateCommentRequest DTO dan gelen comment e
    post = post
)

//DB’de comments tablosunda post_id olmalı ki her yorumun hangi post’a ait olduğu belli olsun.

fun Comment.toResponse()= CommentResponse(
    id= requireNotNull(this.id),
    comment= this.comment
)