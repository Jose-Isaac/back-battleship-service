package com.workshop.battleship.domain.player

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(
    name = "player",
)
data class Player(
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true, length = 20)
    val username: String,

    val password: String,
)
