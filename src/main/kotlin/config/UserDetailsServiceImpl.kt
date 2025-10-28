package org.example.config

import org.example.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepo: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val u = userRepo.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found")
        val auths: Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_${u.role}"))
        return org.springframework.security.core.userdetails.User(u.email, u.password, auths)
    }
}
