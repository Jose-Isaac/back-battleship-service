package com.workshop.battleship.domain.player

import com.workshop.battleship.infrastructure.security.encodePassword
import com.workshop.battleship.resources.player.representation.PlayerProfileRepresentation
import com.workshop.battleship.resources.player.representation.PlayerRepresentation

fun PlayerRepresentation.toEntity() = Player(
    username = this.username,
    password = encodePassword().encode(this.password),
)

fun Player.toProfileRepresentation() = PlayerProfileRepresentation(
    username = this.username,
)
