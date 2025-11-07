package org.example.controller

import jakarta.validation.Valid
import org.example.dto.CreatePostRequest
import org.example.dto.PostResponse
import org.example.dto.UpdatePostRequest
import org.example.mapper.toResponse
import org.example.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class PostController(
    private val postService: PostService
) {
    // CREATE: /users/{userId}/posts
    //admin can create a post for all while user can create a post only with using its own id
    @PreAuthorize("hasRole('ADMIN') or @authz.isSelf(principal?.name, #userId)")
    @PostMapping("/users/{userId}/posts")
    fun addPost(
        @PathVariable userId: Long,
        @Valid @RequestBody req: CreatePostRequest
    ): ResponseEntity<PostResponse> {
        val saved = postService.create(userId, req) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(saved.toResponse())
    }

    // LIST by user: /users/{userId}/posts
    //anyone can see the posts
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/posts")
    fun listUserPosts(
        @PathVariable userId: Long,
        @PageableDefault(size = 10) pageable: Pageable
    ): Page<PostResponse> =
        postService.listByUser(userId, pageable).map { it.toResponse() }

    // GET one post: /posts/{postId}
    // can be seen by anyone who is logged -in
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/posts/{postId}")
    fun getPost(@PathVariable postId: Long): ResponseEntity<PostResponse> =
        postService.getById(postId)
            ?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.notFound().build()


    //only admin or post owner can update
    @PreAuthorize("hasRole('ADMIN') or @authz.ownsPost(principal?.name, #postId)")
    @PutMapping("/posts/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @Valid @RequestBody req: UpdatePostRequest
    ): ResponseEntity<PostResponse> =
        postService.update(postId, req)
            ?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.notFound().build()
    

    // DELETE post: /posts/{postId}
    //only admin or post owner can update
    @PreAuthorize("hasRole('ADMIN') or @authz.ownsPost(principal?.name, #postId)")
    @DeleteMapping("/posts/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<Void> = try {
        postService.delete(postId)
        ResponseEntity.noContent().build()
    } catch (ex: org.springframework.dao.EmptyResultDataAccessException) {
        ResponseEntity.notFound().build()
    }
}
