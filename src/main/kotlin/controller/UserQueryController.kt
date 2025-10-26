package org.example.controller

import org.example.dto.UserWithPostsResponse
import org.example.service.UserQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserQueryController(
    private val userQueryService: UserQueryService
) {
    @GetMapping("/{id}/with-posts")
    fun getUserWithPosts(@PathVariable id: Long): ResponseEntity<UserWithPostsResponse> =
        userQueryService.getUserWithPosts(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

}