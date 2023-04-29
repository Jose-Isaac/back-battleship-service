package com.workshop.battleship.infrastructure.security.config

import com.workshop.battleship.infrastructure.security.encodePassword
import com.workshop.battleship.infrastructure.security.filter.PlayerAuthenticationFilter
import com.workshop.battleship.infrastructure.security.filter.PlayersAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
) {

    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(encodePassword())
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
//        corsConfiguration.allowedOrigins = listOf("http://localhost:3001")
        corsConfiguration.allowedOriginPatterns = listOf("*")
        corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE")
        corsConfiguration.allowCredentials = true
        corsConfiguration.exposedHeaders = listOf(
            "Authorization",
            "Access-Control-Allow-Origin",
            "access_token",
            "refresh_token",
        )

        http.csrf().disable()

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http
            .authorizeHttpRequests()
            .requestMatchers("/token/refresh", "/player/create", "/login", "/ws/**", "/ws")
            .permitAll()
            .and()
            .authorizeHttpRequests()
            .anyRequest()
            .authenticated()
        http
            .cors()
            .configurationSource { corsConfiguration }
        http
            .addFilter(PlayerAuthenticationFilter())
        http
            .addFilterBefore(PlayersAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
