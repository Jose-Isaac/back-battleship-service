package com.workshop.battleship.domain.board

import kotlinx.serialization.Serializable

@Serializable(with = CoordinateSerializer::class)
data class Coordinate(
    val axisX: Int,
    val axisY: Int,
    val occupiedByPlayer: String? = null,
    val isOccupied: Boolean = false,
    val gotHit: Boolean = false,
    val wasHitBy: String? = null,
)
