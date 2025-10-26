package org.example.repository

import org.example.model.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    // "Ali", "ali", "ALİ" does not matter; can find also "Alihan"
    fun findAllByNameContainingIgnoreCase(name: String): List<User>

    @EntityGraph(attributePaths = ["posts"])
    fun findWithPostsById(id: Long): Optional<User>
}