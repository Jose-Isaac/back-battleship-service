package com.workshop.battleship.domain.gameroom

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(
    name = "game_room",
)
data class GameRoom(
    @Id
    val id: UUID = UUID.randomUUID(),

    val boardPlayerOne: UUID,

    val boardPlayerTwo: UUID,

    val playerTurn: String,

    val turn: Int = 1,

    val winner: String? = null,
)
