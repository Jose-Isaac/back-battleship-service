package com.workshop.battleship.resources.gameroom.representation

import com.workshop.battleship.domain.board.Coordinate

data class AttackRepresentation(
    val gameRoomId: String,
    val boardAttackId: String,
    val attackingPlayer: String,
    val coordinate: Coordinate
)
