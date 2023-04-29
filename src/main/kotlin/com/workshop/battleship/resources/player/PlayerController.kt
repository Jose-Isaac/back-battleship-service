package com.workshop.battleship.resources.player

import com.workshop.battleship.domain.model.player.PlayerService
import com.workshop.battleship.domain.player.toEntity
import com.workshop.battleship.domain.player.toProfileRepresentation
import com.workshop.battleship.resources.player.representation.PlayerProfileRepresentation
import com.workshop.battleship.resources.player.representation.PlayerRepresentation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@RestController
@RequestMapping(
    path = ["/player"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
class PlayerController(
    private val playerService: PlayerService,
) {

    @PostMapping("/create")
    fun create(
        @RequestBody playerRepresentation: PlayerRepresentation,
    ): PlayerProfileRepresentation = playerService.createPlayer(playerRepresentation.toEntity()).toProfileRepresentation()
}
