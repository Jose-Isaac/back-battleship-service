package com.workshop.battleship.domain.model.gameroom

interface GameRoomService {
    fun createInvitation(): String
    fun removeInvitation(invitation: String)
}
