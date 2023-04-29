package com.workshop.battleship.domain.player

import kotlinx.serialization.Serializable

@Serializable
data class Credential(
    val username: String = "",
    val password: String = "",
)
