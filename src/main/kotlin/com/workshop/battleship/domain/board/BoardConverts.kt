package com.workshop.battleship.domain.board

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun Board.toVO(): BoardVO {
    return BoardVO(
        id = this.id.toString(),
        plays = Json.decodeFromString(this.plays),
        player = this.player,
    )
}

fun BoardVO.toEntity(): Board {
    return Board(
        id = UUID.fromString(this.id),
        player = this.player,
        plays = Json.encodeToString(this.plays),
    )
}
