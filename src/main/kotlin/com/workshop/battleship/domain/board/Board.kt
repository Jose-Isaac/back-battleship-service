package com.workshop.battleship.domain.board

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(
    name = "board",
)
@kotlinx.serialization.Serializable(with = BoardSerializer::class)
data class Board(
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    val id: UUID = UUID.randomUUID(),

    val plays: String,

    val player: String,
)
