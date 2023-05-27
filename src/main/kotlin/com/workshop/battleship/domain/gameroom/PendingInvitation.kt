package com.workshop.battleship.domain.gameroom

import java.util.UUID

data class PendingInvitation(
    val invitation: String = UUID.randomUUID().toString(),
    val playerUsername: String,
)
