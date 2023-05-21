package com.workshop.battleship.infrastructure.repositories.board

import com.workshop.battleship.domain.board.Board
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BoardRepository : JpaRepository<Board, UUID>
