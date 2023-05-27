package com.workshop.battleship.domain.model.board

import com.workshop.battleship.domain.board.BoardVO
import com.workshop.battleship.domain.board.Coordinate
import java.util.UUID

interface BoardService {
    fun create(playerUsername: String): BoardVO
    fun attackMove(
        boardAttackId: String,
        coordinateAttack: Coordinate,
        attackingPlayerUsername: String,
    ): String
    fun checkWinner(boardAttacked: UUID): Boolean
    fun getBoard(boardId: UUID): BoardVO
}
