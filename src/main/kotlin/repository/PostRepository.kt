package org.example.repository

import org.example.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Post>
}