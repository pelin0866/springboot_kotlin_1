package org.example.controller

import jakarta.validation.Valid
import org.example.dto.CommentResponse
import org.example.dto.CreateCommentRequest
import org.example.dto.UpdateCommentRequest
import org.example.mapper.toResponse
import org.example.service.CommentService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class CommentController(
    private val commentService: CommentService
) {

    // POST /posts/{post_id}/comments
    @PostMapping("/posts/{postId}/comments")
    fun addComment(
        @PathVariable("postId") postId: Long,
        @Valid @RequestBody req: CreateCommentRequest
    ):ResponseEntity<CommentResponse>{
        val saved = commentService.create(postId, req)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(saved.toResponse())
    }

    // GET /posts/{post_id}/comments?page=0&size=10&sort=id,desc
    @GetMapping("/posts/{post_id}/comments")
    fun listComment(
        @PathVariable("postId") postId: Long,
        @ParameterObject @PageableDefault(size=10) pageable: Pageable
    ): Page<CommentResponse> = commentService.listByPost(postId, pageable).map {it.toResponse()}

    // GET /posts/{post_id}/comments/{comment_id}
    @GetMapping("/posts/{post_id}/comments/{comment_id}")
    fun getCommentById(
        @PathVariable("postId") postId: Long,
        @PathVariable("commentId") commentId: Long
    ): ResponseEntity<CommentResponse>{
        val found = commentService.getById(commentId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(found.toResponse())
    }

    // PUT /posts/{post_id}/comments/{comment_id}
    @PutMapping("/posts/{postId}/comments/{commentId}")
    fun updateComment(
        @PathVariable("postId") postId: Long,
        @PathVariable("commentId") commentId: Long,
        @Valid @RequestBody req: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> =
        commentService.update(postId, commentId, req)
            ?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.notFound().build()


    // DELETE /comments/{comment_id}
    @DeleteMapping("/comments/{comment_id}")
    fun deleteComment(
        @PathVariable("commentId") commentId: Long
    ): ResponseEntity<Void> = try{
        commentService.delete(commentId)
        ResponseEntity.noContent().build()
    } catch (ex: org.springframework.dao.EmptyResultDataAccessException){
        ResponseEntity.notFound().build()
    }
}