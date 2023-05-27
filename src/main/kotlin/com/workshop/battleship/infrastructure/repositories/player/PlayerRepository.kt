package com.workshop.battleship.infrastructure.repositories.player

import com.workshop.battleship.domain.player.Player
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface PlayerRepository : JpaRepository<Player, UUID> {
    fun findByUsername(username: String): Optional<Player>
}
