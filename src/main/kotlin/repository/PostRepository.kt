package org.example.repository

import org.example.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Post>

    @EntityGraph(attributePaths = ["comments"])
    fun findWithCommentsById(id: Long): Optional<Post>
}