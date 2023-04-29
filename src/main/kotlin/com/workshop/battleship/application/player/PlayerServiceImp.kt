package com.workshop.battleship.application.player

import com.workshop.battleship.infrastructure.repositories.player.PlayerRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PlayerServiceImp(
    private val playerRepository: PlayerRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val player = playerRepository.findByUsername(username)

        if (player.isEmpty) {
            throw UsernameNotFoundException("Player not found!")
        }

        val authorities: List<SimpleGrantedAuthority> = emptyList()

        return User(player.get().username, player.get().password, authorities)
    }
}
