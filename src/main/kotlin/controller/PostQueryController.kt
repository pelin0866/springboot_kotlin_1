package org.example.controller

import org.example.dto.PostWithCommentsResponse
import org.example.service.PostQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/post")
class PostQueryController(
    private val postQueryService: PostQueryService
) {
    @GetMapping()
    fun getPostWithComments(@PathVariable id: Long): ResponseEntity<PostWithCommentsResponse> =
        postQueryService.getPostWithComments(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}