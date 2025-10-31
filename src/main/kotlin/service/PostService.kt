package org.example.service

import org.example.dto.CreatePostRequest
import org.example.dto.UpdatePostRequest
import org.example.mapper.applyTo
import org.example.mapper.toEntity
import org.example.model.Post
import org.example.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userService: UserService // to control also user
) {
    @Transactional
    fun create(userId: Long, req: CreatePostRequest): Post? {
        val user = userService.getUserById(userId) ?: return null
        return postRepository.save(req.toEntity(user))
    }

    fun getById(postId: Long): Post? =
        postRepository.findById(postId).orElse(null)

    fun listByUser(userId: Long, pageable: Pageable): Page<Post> =
        postRepository.findAllByUserId(userId, pageable)

    @Transactional
    fun delete(postId: Long) =
        postRepository.deleteById(postId)

    @Transactional
    fun update(postId: Long, req: UpdatePostRequest): Post? {
        val existing = postRepository.findById(postId).orElse(null) ?: return null
        val updated = req.applyTo(existing)
        return postRepository.save(updated)
    }
}
