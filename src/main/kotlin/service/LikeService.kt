package org.example.service

import org.example.model.Like
import org.example.repository.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val userService: UserService,
    private val postService: PostService
) {
    fun like(userId: Long, postId: Long): Boolean{
        if(likeRepository.existsByUserIdAndPostId(userId, postId)) return false

        val user = userService.getUserById(userId) ?: return false
        val post = postService.getById(postId) ?: return false

        likeRepository.save(Like(user =  user, post = post))
        return true
    }

    fun unlike(userId: Long, postId: Long): Boolean =
        likeRepository.deleteByUserIdAndPostId(userId, postId) > 0

    fun count(postId: Long): Long = likeRepository.countByPostId(postId)
}