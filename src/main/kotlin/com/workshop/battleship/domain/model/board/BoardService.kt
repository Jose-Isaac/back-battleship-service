package com.workshop.battleship.domain.model.board

import com.workshop.battleship.domain.board.BoardVO

interface BoardService {
    fun create(playerUsername: String): BoardVO
}
