package com.workshop.battleship.resources.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.workshop.battleship.domain.model.player.PlayerService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.GrantedAuthority
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(
    path = ["/token"],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
)
class SecurityController(
    private val playerService: PlayerService,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                val token: String = authorizationHeader.substring("Bearer".length).replace(" ", "")
                val algorithm: Algorithm = Algorithm.HMAC256("secret".toByteArray()) // TODO refactor this use secret
                val verifier: JWTVerifier = JWT.require(algorithm).build()
                val decodedJWT: DecodedJWT = verifier.verify(token)
                val username: String = decodedJWT.subject
                val player = playerService.getPlayer(username)
                val accessToken = JWT.create()
                    .withSubject(player.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.requestURI.toString())
                    .withClaim("roles", emptyArray<GrantedAuthority>().map(GrantedAuthority::getAuthority).toList())
                    .sign(algorithm)
                response.status = HttpStatus.CREATED.value()
                response.setHeader("access_token", accessToken)
                response.setHeader("refresh_token", token)
                // TODO add handle exception
            } catch (ex: Exception) {
                logger.error("Error logging in: ${ex.message}")
                response.status = HttpStatus.FORBIDDEN.value()
            }
        } else {
            throw RuntimeException("Refresh token is missing")
        }
    }
}
