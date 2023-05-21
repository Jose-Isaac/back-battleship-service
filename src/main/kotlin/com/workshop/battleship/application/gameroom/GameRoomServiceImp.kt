package com.workshop.battleship.application.gameroom

import com.workshop.battleship.domain.gameroom.*
import com.workshop.battleship.domain.model.board.BoardService
import com.workshop.battleship.domain.model.gameroom.GameRoomService
import com.workshop.battleship.domain.player.ActivityType
import com.workshop.battleship.infrastructure.repositories.gameroom.GameRoomRepository
import com.workshop.battleship.infrastructure.security.utils.getCurrentPlayerInContext
import com.workshop.battleship.resources.gameroom.representation.JoinRepresentation
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameRoomServiceImp(
    private val gameRoomRepository: GameRoomRepository,
    private val boardService: BoardService,
    private val simpMessageTemplate: SimpMessagingTemplate,
) : GameRoomService {
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

    override fun join(join: JoinRepresentation) {
        val pendingInvitation = pendingInvitations.find { it.invitation == join.inviteId }

        if (pendingInvitation == null) {
            logger.warn("Invitation: ${join.inviteId} not found")
            throw Exception("Invitation: ${join.inviteId} not found")
            // TODO create not found exception
        }

        val gameRoom = createRoom(pendingInvitation, join.username)

        pendingInvitations.remove(pendingInvitation)

        logger.info("Response representation: ${gameRoom.toRepresentation(ActivityType.JOIN)}")

        val response = gameRoom.toRepresentation(ActivityType.JOIN)

        simpMessageTemplate.convertAndSend(
            "/topic/gameroom/${join.inviteId}/response",
            Json.encodeToString(response),
        )
    }

    override fun createRoom(pendingInvitation: PendingInvitation, currentPlayer: String): GameRoomVO {
        logger.info("START - creating a new game room")

        val boardForPlayerOne = boardService.create(pendingInvitation.playerUsername)
        val boardForPlayerTwo = boardService.create(currentPlayer)

        val gameRoom = gameRoomRepository.save(
            GameRoom(
                boardPlayerOne = UUID.fromString(boardForPlayerOne.id),
                boardPlayerTwo = UUID.fromString(boardForPlayerTwo.id),
                playerTurn = currentPlayer, // TODO create sort initial player
            ),
        )
        logger.info("END - created a new game room")
        return gameRoom.toVO(boardForPlayerOne, boardForPlayerTwo)
    }
}
