package org.example.api

import org.example.repository.CommentRepository
import org.example.repository.LikeRepository
import org.example.repository.PostRepository
import org.example.repository.UserRepository
import org.springframework.stereotype.Component

@Component("authz")
class AuthzService(
    private val users: UserRepository,
    private val posts: PostRepository,
    private val comments: CommentRepository,
    private val likes: LikeRepository
) {
    // checks that user is itself
    fun isSelf(email: String?, userId: Long): Boolean =
        email != null && users.findById(userId).map { it.email == email }.orElse(false)

    // checks that user has mentioned post
    fun ownsPost(email: String?, postId: Long): Boolean =
        email != null && posts.findById(postId).map { it.user.email == email }.orElse(false)
    /** principal.email ilgili comment’in bağlı olduğu post’un sahibi mi? */
    fun ownsComment(email: String?, commentId: Long): Boolean =
        email != null && comments.findById(commentId).map { it.post.user.email == email }.orElse(false)

    /** principal.email bu postu like’lamış mı? (only owner can unlike) */
    fun likedBy(email: String?, postId: Long): Boolean {
        if (email == null) return false
        val u = users.findByEmail(email) ?: return false
        return likes.existsByUserIdAndPostId(u.id!!, postId)
    }
}
