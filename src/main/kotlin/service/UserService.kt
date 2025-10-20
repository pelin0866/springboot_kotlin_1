package org.example.service

import org.example.model.User
import org.example.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.annotation.Id
import org.springframework.stereotype.Service
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


@Service
class UserService(private val userRepository: UserRepository) {
    fun getAllUsers(pageable: Pageable): Page<User> = userRepository.findAll(pageable)

    fun createUser(user: User): User = userRepository.save(user)

    fun getUserByEmail(email: String): User? = userRepository.findByEmail(email)

    //isme gore arama: parca eslesmesı ve case sensetive
    fun searchUsersByName(name: String): List<User> = userRepository.findAllByNameContainingIgnoreCase(name)

    fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)
    //Hazır gelen metotlar (findById, findAllById vs.) → Java API’sinden miras, bu yüzden Optional<T> döner.
    //Bu yüzden findById(id) bize Optional<User> döndürüyor, biz de .orElse(null) ile User?’a çeviriyoruz.

    fun deleteById(id: Long) = userRepository.deleteById(id)

    fun updateUser(id: Long, name: String, email: String): User? {
        val existing = userRepository.findById(id).orElse(null) ?: return null
        val updated = existing.copy(name = name, email = email)
        return try {
            userRepository.save(updated)
        } catch (ex: DataIntegrityViolationException) {
            // email unique ihlali gibi durumlarda 409 vereceğiz (Controller’dan)
            throw ex
        }
    }
}