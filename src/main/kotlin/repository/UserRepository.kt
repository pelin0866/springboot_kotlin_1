package org.example.repository

import org.example.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    // "Ali", "ali", "ALİ" fark etmez; "Alihan" gibi parça eşleşmelerini de getirir
    fun findAllByNameContainingIgnoreCase(name: String): List<User>


}