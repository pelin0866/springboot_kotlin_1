package org.example.config


import org.example.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        // email as username
        val u = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        val authorities = listOf(SimpleGrantedAuthority("ROLE_${u.role.name}"))

        return User(
            u.email,            // username
            u.password,         // BCrypt hash
            true, true, true, true,
            authorities
        )
    }
}
