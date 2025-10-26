package org.example.service

import org.example.dto.UserWithPostsResponse
import org.example.mapper.toWithPostsResponse
import org.example.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userRepository: UserRepository
) {
    fun getUserWithPosts(id: Long): UserWithPostsResponse? =
        userRepository.findWithPostsById(id).orElse(null)?.toWithPostsResponse()
}