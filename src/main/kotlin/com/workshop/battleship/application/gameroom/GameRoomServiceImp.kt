package com.workshop.battleship.application.gameroom

import com.workshop.battleship.domain.gameroom.*
import com.workshop.battleship.domain.model.gameroom.GameRoomService
import com.workshop.battleship.infrastructure.security.utils.getCurrentPlayerInContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GameRoomServiceImp : GameRoomService {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val pendingInvitations = mutableListOf<PendingInvitation>()

    override fun createInvitation(): String {
        val pendingInvitation = PendingInvitation(
            playerUsername = getCurrentPlayerInContext(),
        )

        pendingInvitations.add(pendingInvitation)

        return pendingInvitation.invitation
    }

    override fun removeInvitation(invitation: String) {
        pendingInvitations.removeIf { it.invitation == invitation }
    }
}
