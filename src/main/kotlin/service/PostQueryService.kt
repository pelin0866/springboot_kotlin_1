package org.example.service

import org.example.dto.PostWithCommentsResponse
import org.example.mapper.toWithCommentsResponse
import org.example.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostQueryService(
    private val postRepository: PostRepository
) {
    fun getPostWithComments(id:Long): PostWithCommentsResponse? =
        postRepository.findWithCommentsById(id).orElse(null).toWithCommentsResponse()
}