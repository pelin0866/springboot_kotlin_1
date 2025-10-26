package org.example.repository

import jakarta.persistence.Id
import org.example.model.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository: JpaRepository<Like, Long> {
    fun existsByUserIdAndPostId(userId: Long, postId:Long): Boolean
    fun countByPostId(postId: Long): Long
    fun deleteByUserIdAndPostId(userId: Long, postId: Long): Long
}