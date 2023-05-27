package com.workshop.battleship.infrastructure.repositories.gameroom

import com.workshop.battleship.domain.gameroom.GameRoom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GameRoomRepository : JpaRepository<GameRoom, UUID>
