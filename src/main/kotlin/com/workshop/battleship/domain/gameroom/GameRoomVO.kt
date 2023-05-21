package com.workshop.battleship.domain.gameroom

import com.workshop.battleship.domain.board.BoardVO
import java.util.UUID

data class GameRoomVO(
    val id: UUID = UUID.randomUUID(),
    val boardPlayerOne: BoardVO,
    val boardPlayerTwo: BoardVO,
    val playerTurn: String,
    val turn: Int = 1,
    val winner: String? = null,
)
