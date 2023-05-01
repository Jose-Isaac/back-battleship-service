package com.workshop.battleship.domain.model.player

import com.workshop.battleship.domain.player.Player

interface PlayerService {
    fun createPlayer(player: Player): Player
    fun getPlayer(username: String): Player
}
