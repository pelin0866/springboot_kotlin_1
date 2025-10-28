package org.example.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtService {
    // NOT: production'da .env / config'den oku
    private val key = Keys.hmacShaKeyFor("super-secret-key-change-me-please-32bytes!".toByteArray())

    fun generate(email: String, role: String, minutes: Long = 60): String =
        Jwts.builder()
            .setSubject(email)
            .claim("role", role)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + minutes * 60_000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

    fun parse(emailToken: String): String? =
        runCatching {
            val claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(emailToken).body
            claims.subject
        }.getOrNull()
}
