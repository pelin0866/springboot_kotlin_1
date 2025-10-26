package org.example.controller

import org.example.service.LikeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/posts/{postId}/likes")
class LikeController(
    private val likeService: LikeService
) {
    //POST /posts/{postId}/likes/{userId}
    @PostMapping("/{userId}")
    fun like(
        @PathVariable postId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<Void> =
        if (likeService.like(userId, postId))
            ResponseEntity.noContent().build()
        else
            ResponseEntity.status(409).build() //409 conflict, liked already
    // DELETE /posts/{postId}/likes/{userId}
    @DeleteMapping("/{userId}")
    fun unlike(
        @PathVariable postId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<Void> =
        if (likeService.unlike(userId, postId))
            ResponseEntity.noContent().build()
        else
            ResponseEntity.notFound().build()

    // GET /posts/{postId}/likes/count
    @GetMapping("/count")
    fun count(
        @PathVariable postId: Long
    ): ResponseEntity<Long> =
        ResponseEntity.ok(likeService.count(postId))
}