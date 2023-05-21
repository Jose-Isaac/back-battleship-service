package com.workshop.battleship.domain.board

@kotlinx.serialization.Serializable
data class BoardVO(
    val id: String,
    val plays: MutableList<MutableList<Coordinate>>,
    val player: String,
)
