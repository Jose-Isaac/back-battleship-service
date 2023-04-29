package com.workshop.battleship.infrastructure.security.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class PlayersAuthorizationFilter() : OncePerRequestFilter() {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (!request.servletPath.equals("/login") && !request.servletPath.equals("/token/refresh")) {
            val authorizationHeader = request.getHeader(AUTHORIZATION)
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    val token: String = authorizationHeader.substring("Bearer ".length)
                    val algorithm: Algorithm = Algorithm.HMAC256("secret".toByteArray()) // TODO refactor this use secret
                    val verifier: JWTVerifier = JWT.require(algorithm).build()
                    val decodedJWT: DecodedJWT = verifier.verify(token)
                    val username: String = decodedJWT.subject
                    val roles: Array<String> = decodedJWT.getClaim("roles").asArray(String::class.java)
                    val authorities: List<SimpleGrantedAuthority> = roles.map { SimpleGrantedAuthority(it) }
                    val authenticationToken = UsernamePasswordAuthenticationToken(username, null, authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                } catch (ex: Exception) {
                    logger.error("Error logging in: ${ex.message}")
//                    response.status = HttpStatus.FORBIDDEN.value()

                    response.status = HttpStatus.OK.value()
                    // TODO add response body with error message
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
