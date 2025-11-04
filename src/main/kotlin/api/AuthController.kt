package org.example.api

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.example.config.JwtService
import org.example.model.Role
import org.example.model.User
import org.example.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val users: UserRepository,
    private val encoder: PasswordEncoder,
    private val authManager: AuthenticationManager,
    private val jwt: JwtService
) {

    data class RegisterRequest(
        @field:NotBlank val name: String,
        @field:Email val email: String,
        @field:NotBlank val password: String
    )
    data class LoginRequest(
        @field:Email val email: String,
        @field:NotBlank val password: String
    )
    data class AuthResponse(val token: String)
    data class MeResponse(val email: String, val name: String, val role: String)

    // POST /auth/register  -> kullanıcıyı DB'ye kaydet + JWT döndür
    @PostMapping("/register")
    fun register(@Valid @RequestBody req: RegisterRequest): ResponseEntity<AuthResponse> {
        // email benzersizliği (hızlı kontrol) – ayrıca DB'de unique constraint var
        if (users.findByEmail(req.email) != null) {
            return ResponseEntity.status(409).build() // 409 Conflict
        }
        val entity = User(
            name = req.name,
            email = req.email,
            password = encoder.encode(req.password), // BCrypt hash
            role = Role.USER
        )
        val saved = users.save(entity)
        val token = jwt.generate(saved.email, saved.role.name)
        return ResponseEntity.ok(AuthResponse(token))
    }

    // POST /auth/login     -> kimlik doğrula + JWT döndür
    @PostMapping("/login")
    fun login(@Valid @RequestBody req: LoginRequest): ResponseEntity<AuthResponse> {
        // Spring Security doğrulaması (UserDetailsService + BCrypt)
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(req.email, req.password)
        )
        val u = users.findByEmail(req.email)!! // authenticate başarılıysa user kesin vardır
        val token = jwt.generate(u.email, u.role.name)
        return ResponseEntity.ok(AuthResponse(token))
    }

    // GET /auth/me         -> JWT filtresi SecurityContext'i doldurdu; principal.name = email
    @GetMapping("/me")
    fun me(principal: java.security.Principal?): ResponseEntity<MeResponse> {
        principal ?: return ResponseEntity.status(401).build()
        val u = users.findByEmail(principal.name) ?: return ResponseEntity.status(401).build()
        return ResponseEntity.ok(MeResponse(email = u.email, name = u.name, role = u.role.name))
    }
}
