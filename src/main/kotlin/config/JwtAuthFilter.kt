package org.example.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwt: JwtService,
    private val uds: UserDetailsServiceImpl
) : OncePerRequestFilter() {

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val header = req.getHeader("Authorization")
        val token = header?.takeIf { it.startsWith("Bearer ") }?.removePrefix("Bearer ")
        if (token != null && SecurityContextHolder.getContext().authentication == null) {
            val username = jwt.parse(token)
            if (username != null) {
                val details = uds.loadUserByUsername(username)
                val auth = UsernamePasswordAuthenticationToken(details, null, details.authorities)
                auth.details = WebAuthenticationDetailsSource().buildDetails(req)
                SecurityContextHolder.getContext().authentication = auth
            }
        }
        chain.doFilter(req, res)
    }
}
