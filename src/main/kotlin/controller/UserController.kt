package org.example.controller

import jakarta.validation.Valid
import org.example.dto.CreateUserRequest
import org.example.dto.UpdateUserRequest
import org.example.dto.UserResponse
import org.example.mapper.toEntity
import org.example.mapper.toResponse
import org.example.model.User
import org.example.service.UserService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    //only admin can list all users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAll(
        @PageableDefault(size = 10, sort = ["id"]) pageable: org.springframework.data.domain.Pageable
    ): org.springframework.data.domain.Page<UserResponse> {
        // DEBUG log
        println("page=${pageable.pageNumber}, size=${pageable.pageSize}, sort=${pageable.sort}")

        return userService.getAllUsers(pageable).map { it.toResponse() }
    }

    //only can admin create a user
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun create(@Valid @RequestBody req: CreateUserRequest): ResponseEntity<UserResponse> {
        val saved = userService.createUser(req.toEntity())
        val body = saved.toResponse()
        return ResponseEntity
            .created(URI.create("/users/${body.id}")) // Location header
            .body(body) // 201 Created
    }

    //users can see only its own profile but admin can see all
    @PreAuthorize("hasRole('ADMIN') or @authz.isSelf(principal?.name, #id)")
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponse> =
        userService.getUserById(id)
            ?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.notFound().build()

    //users can see only its own email while admin can see all
    @PreAuthorize("hasRole('ADMIN') or #mail == principal?.name")
    @GetMapping("/by-email")
    fun getByEmail(@RequestParam mail: String): ResponseEntity<UserResponse> =
        userService.getUserByEmail(mail)
            ?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.notFound().build()

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-name")
    fun getByName(@RequestParam name: String): List<UserResponse> =
        userService.searchUsersByName(name).map { it.toResponse() }

    //user can update only its own profile while admin can update all
    @PreAuthorize("hasRole('ADMIN') or @authz.isSelf(principal?.name, #id)")
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody req: UpdateUserRequest
    ): ResponseEntity<UserResponse> = try {
        val updated = userService.updateUser(id, req.name, req.email)
        if (updated != null) ResponseEntity.ok(updated.toResponse())
        else ResponseEntity.notFound().build()
    } catch (ex: DataIntegrityViolationException) {
        ResponseEntity.status(409).build() // conflicting emails -> 409 Conflict
    }

    //only admin can delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> = try {
        userService.deleteById(id)
        ResponseEntity.noContent().build()
    } catch (ex: org.springframework.dao.EmptyResultDataAccessException) {
        ResponseEntity.notFound().build()
    }
}

