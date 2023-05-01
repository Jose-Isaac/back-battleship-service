package com.workshop.battleship.application.player

import com.workshop.battleship.domain.model.player.PlayerService
import com.workshop.battleship.domain.player.Player
import com.workshop.battleship.infrastructure.repositories.player.PlayerRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PlayerServiceImp(
    private val playerRepository: PlayerRepository,
) : PlayerService, UserDetailsService {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun loadUserByUsername(username: String): UserDetails {
        val player = playerRepository.findByUsername(username)

        if (player.isEmpty) {
            throw UsernameNotFoundException("Player not found!")
        }

        val authorities: List<SimpleGrantedAuthority> = emptyList()

        return User(player.get().username, player.get().password, authorities)
    }

    override fun createPlayer(player: Player): Player {
        logger.info("START create a new player: ${player.username}")

        val newPlayer = playerRepository.save(player)

        logger.info("END created a new player: ${newPlayer.username}")
        return newPlayer
    }

    override fun getPlayer(username: String): Player {
        val optional = playerRepository.findByUsername(username)

        if (optional.isPresent) {
            return optional.get()
        }

        throw Exception("Player not found")
    }
}
