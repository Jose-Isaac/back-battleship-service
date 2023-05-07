package com.workshop.battleship.resources.gameroom

import com.workshop.battleship.domain.model.gameroom.GameRoomService
import com.workshop.battleship.resources.gameroom.representation.InviteResponseRepresentation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(
    path = ["/gameroom"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
class GameRoomController(
    private val gameRoomService: GameRoomService,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/create/invite")
    fun createInvite(): InviteResponseRepresentation =
        InviteResponseRepresentation(
            invite = gameRoomService.createInvitation(),
        )
}
