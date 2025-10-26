package org.example.service

import org.example.dto.CreatePostRequest
import org.example.mapper.toEntity
import org.example.model.Post
import org.example.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userService: UserService // to control also user
) {
    fun create(userId: Long, req: CreatePostRequest): Post? {
        val user = userService.getUserById(userId) ?: return null
        return postRepository.save(req.toEntity(user))
    }

    fun getById(postId: Long): Post? =
        postRepository.findById(postId).orElse(null)

    fun listByUser(userId: Long, pageable: Pageable): Page<Post> =
        postRepository.findAllByUserId(userId, pageable)

    fun delete(postId: Long) =
        postRepository.deleteById(postId)

    fun update(postId: Long, title: String, content: String): Post? {
        val existing = postRepository.findById(postId).orElse(null) ?: return null
        val updated = existing.copy(title = title, content = content)
        return postRepository.save(updated)
    }
}
