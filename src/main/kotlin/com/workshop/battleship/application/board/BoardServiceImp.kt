package com.workshop.battleship.application.board

import com.workshop.battleship.domain.board.*
import com.workshop.battleship.domain.model.board.BoardService
import com.workshop.battleship.infrastructure.repositories.board.BoardRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class BoardServiceImp(
    private val boardRepository: BoardRepository,
) : BoardService {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun create(playerUsername: String): BoardVO {
        logger.info("START - creating a new board")
        val initialPlays = populateInitialPlays()
        val axisX = 5
        initialPlays[axisX] = mockInitialPosition(playerUsername, axisX)

        val board = boardRepository.save(
            Board(
                plays = Json.encodeToString(initialPlays),
                player = playerUsername,
            ),
        )

        logger.info("END - created a new board with initial plays: ${board.plays}")

        return board.toVO()
    }

    override fun attackMove(
        boardAttackId: String,
        coordinateAttack: Coordinate,
        attackingPlayerUsername: String,
    ): String {
        val boardOptional = boardRepository.findById(
            UUID.fromString(boardAttackId),
        )

        if (boardOptional.isEmpty) {
            logger.warn("Board not found")
            throw Exception("Board not found")
            // TODO create not found exception
        }

        val boardAttack = boardOptional.get().toVO()

        logger.info("START - player: $attackingPlayerUsername attacking board: $boardAttackId")

        // TODO valid coordinate

        var coordinate = boardAttack.plays[coordinateAttack.axisX][coordinateAttack.axisY]

        coordinate = coordinate.copy(
            gotHit = true,
            wasHitBy = attackingPlayerUsername,
        )

        boardAttack.plays[coordinateAttack.axisX][coordinateAttack.axisY] = coordinate

        boardRepository.save(
            boardAttack.toEntity(),
        )

        return boardAttack.player
    }

    override fun checkWinner(boardAttacked: UUID): Boolean {
        val board = boardRepository.findById(boardAttacked).get().toVO()

        return allShipsAreSunk(board)
    }

    override fun getBoard(boardId: UUID): BoardVO {
        return boardRepository.findById(boardId).get().toVO()
    }

    private fun populateInitialPlays(): MutableList<MutableList<Coordinate>> {
        // TODO create constant for number of line and column in board
        return MutableList(6) { axisX ->
            MutableList(6) { axisY -> Coordinate(axisX, axisY) }
        }
    }

    private fun mockInitialPosition(playerUsername: String, axisX: Int): MutableList<Coordinate> {
        return MutableList(6) {
            (it <= 3)
            Coordinate(
                axisX = axisX,
                axisY = it,
                occupiedByPlayer = playerUsername,
                isOccupied = true,
            )
        }
    }

    private fun allShipsAreSunk(board: BoardVO): Boolean {
        board.plays.forEach { line ->
            line.forEach { if (it.isOccupied && !it.gotHit) return false }
        }

        return true
    }
}
