package org.example.controller

import jakarta.validation.Valid
import org.example.dto.*
import org.example.model.Role
import org.example.repository.UserRepository
import org.example.config.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepo: UserRepository,
    private val encoder: PasswordEncoder,
    private val authManager: AuthenticationManager,
    private val jwt: JwtService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody req: RegisterRequest): ResponseEntity<AuthResponse> {
        if (userRepo.findByEmail(req.email) != null) return ResponseEntity.status(409).build()
        val user = org.example.model.User(
            name = req.name,
            email = req.email
        ).apply {
            password = encoder.encode(req.password)
            role = Role.USER
        }
        val saved = userRepo.save(user)
        val token = jwt.generate(saved.email, saved.role.name)
        return ResponseEntity.ok(AuthResponse(token))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody req: LoginRequest): ResponseEntity<AuthResponse> {
        authManager.authenticate(UsernamePasswordAuthenticationToken(req.email, req.password))
        val u = userRepo.findByEmail(req.email)!!
        val token = jwt.generate(u.email, u.role.name)
        return ResponseEntity.ok(AuthResponse(token))
    }
}
