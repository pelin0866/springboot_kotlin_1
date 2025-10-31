package org.example.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }                 // disable CSRF protection
            .authorizeHttpRequests {
                it.anyRequest().permitAll()        // For all endpoints = permilAll
            }
            .build()
}

//@Configuration
//class SecurityConfig(
//    private val jwtFilter: JwtAuthFilter,
//    private val userDetailsService: UserDetailsServiceImpl
//) {
//
//    @Bean fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
//
//    @Bean
//    fun authenticationManager(): AuthenticationManager {
//        val p = DaoAuthenticationProvider()
//        p.setUserDetailsService(userDetailsService)
//        p.setPasswordEncoder(passwordEncoder())
//        return ProviderManager(p)
//    }
//
//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain =
//        http.csrf { it.disable() }
//            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            .authorizeHttpRequests {
//                it.requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
//                it.requestMatchers("/users/**").hasAnyRole("USER","ADMIN")
//                it.anyRequest().authenticated()
//            }
//            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
//            .build()
//}