package org.example.service

import org.example.dto.CreateCommentRequest
import org.example.dto.UpdateCommentRequest
import org.example.mapper.applyTo
import org.example.mapper.toEntity
import org.example.model.Comment
import org.example.repository.CommentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postService: PostService
) {

    @Transactional
    fun create(postId: Long, req: CreateCommentRequest): Comment? {
        val post = postService.getById(postId) ?: return null
        return commentRepository.save(req.toEntity(post))
    }

    @Transactional(readOnly = true)
    fun listByPost(postId: Long, pageable: Pageable): Page<Comment> =
        commentRepository.findAllByPostId(postId, pageable)

    @Transactional(readOnly = true)
    fun getById(commentId: Long): Comment? =
        commentRepository.findById(commentId).orElse(null)

    @Transactional
    fun update(postId: Long, commentId: Long, req: UpdateCommentRequest): Comment?{
        val existing = commentRepository.findById(commentId).orElse(null) ?: return null
        if (existing.post.id != postId) return null
        val updated = req.applyTo(existing)
        return commentRepository.save(updated)
    }

    @Transactional
    fun delete(commentId: Long) = commentRepository.deleteById(commentId)
}