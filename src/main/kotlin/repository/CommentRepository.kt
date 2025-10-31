package org.example.repository

import org.example.model.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByPostId(postId: Long, pageable: Pageable): Page<Comment> //all comments
    fun findByIdAndPostId(id: Long, postId: Long): Comment? //specific comment
}
