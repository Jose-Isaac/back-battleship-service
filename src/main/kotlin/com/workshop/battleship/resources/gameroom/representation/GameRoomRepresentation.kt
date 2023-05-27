package com.workshop.battleship.resources.gameroom.representation

import com.workshop.battleship.domain.board.BoardVO
import com.workshop.battleship.domain.player.ActivityType

@kotlinx.serialization.Serializable
data class GameRoomRepresentation(
    val activityType: ActivityType,
    val gameRoomId: String,
    val playerOne: String,
    val playerTwo: String,
    val boardOne: BoardVO,
    val boardTwo: BoardVO,
    val playerTurn: String,
    val turn: Int,
    val winner: String?,
)
