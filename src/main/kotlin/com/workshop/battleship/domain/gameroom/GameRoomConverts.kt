package com.workshop.battleship.domain.gameroom

import com.workshop.battleship.domain.board.BoardVO
import com.workshop.battleship.domain.player.ActivityType
import com.workshop.battleship.resources.gameroom.representation.GameRoomRepresentation

fun GameRoomVO.toRepresentation(activityType: ActivityType): GameRoomRepresentation {
    return GameRoomRepresentation(
        activityType = activityType,
        gameRoomId = this.id.toString(),
        playerOne = this.boardPlayerOne.player,
        boardOne = this.boardPlayerOne,
        playerTwo = this.boardPlayerTwo.player,
        boardTwo = this.boardPlayerTwo,
        playerTurn = this.playerTurn,
        turn = this.turn,
        winner = this.winner,
    )
}

fun GameRoom.toVO(boardPlayerOne: BoardVO, boardPlayerTwo: BoardVO): GameRoomVO {
    return GameRoomVO(
        id = this.id,
        boardPlayerOne = boardPlayerOne,
        boardPlayerTwo = boardPlayerTwo,
        playerTurn = this.playerTurn,
        turn = this.turn,
        winner = this.winner,
    )
}
