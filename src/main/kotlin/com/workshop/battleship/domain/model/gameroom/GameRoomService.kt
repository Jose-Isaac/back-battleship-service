package com.workshop.battleship.domain.model.gameroom

import com.workshop.battleship.domain.gameroom.GameRoomVO
import com.workshop.battleship.domain.gameroom.PendingInvitation
import com.workshop.battleship.resources.gameroom.representation.JoinRepresentation

interface GameRoomService {
    fun createInvitation(): String
    fun removeInvitation(invitation: String)
    fun join(join: JoinRepresentation)
    fun createRoom(pendingInvitation: PendingInvitation, currentPlayer: String): GameRoomVO
}
