package org.example.service

import org.example.dto.CreateCommentRequest
import org.example.mapper.toEntity
import org.example.model.Comment
import org.example.repository.CommentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postService: PostService
) {

    fun create(postId: Long, req: CreateCommentRequest): Comment? {
        val post = postService.getById(postId) ?: return null
        return commentRepository.save(req.toEntity(post))
    }

    fun listByPost(postId: Long, pageable: Pageable): Page<Comment> =
        commentRepository.findAllByPostId(postId, pageable)

    fun getById(commentId: Long): Comment? =
        commentRepository.findById(commentId).orElse(null)

    fun update(postId: Long, commentId: Long, newComment: String): Comment?{
        val existing = commentRepository.findById(commentId).orElse(null) ?: return null
        if (existing.post.id != postId) return null
        val updated = existing.copy(comment = newComment)
        return commentRepository.save(updated)
    }

    fun delete(commentId: Long) = commentRepository.deleteById(commentId)
}