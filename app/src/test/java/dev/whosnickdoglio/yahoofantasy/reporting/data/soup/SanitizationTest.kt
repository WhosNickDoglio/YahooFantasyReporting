// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

@file:Suppress("JUnitMalformedDeclaration")

package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import app.cash.burst.Burst
import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

internal class SanitizationTest {

    private val rawPlayerNameValues = listOf(
        "Cade CunninghamPlayer Note DET - PG,SG",
        "Donovan MitchellPlayer Note CLE - PG,SG",
        "Austin ReavesNew Player Note LAL - PG,SG",
        "OG AnunobyGTDNew Player Note NYK - SF,PF",
        "Keegan MurrayINJVideo ForecastPlayer Note SAC - SF,PF",
        "Zach EdeyINJNo new player Notes MEM - C",
        "Cade CunninghamPlayer Note DET - PG,SG 7:00 pm vs CHI",
        "Donovan MitchellPlayer Note CLE - PG,SG 7:30 pm @ MIA",
    )
    enum class PlayerHealthValues(val input: String, val expected: PlayerHealthStatus) {
        NO_GAME_HEALTHY("Cade CunninghamPlayer Note DET - PG,SG", PlayerHealthStatus.HEALTHY),
        GAME_HEALTHY("Cade CunninghamPlayer Note DET - PG,SG 7:00 pm vs CHI", PlayerHealthStatus.HEALTHY),
        NO_GAME_INJURED("Zach EdeyINJNo new player Notes MEM - C", PlayerHealthStatus.INJURED),
        NO_GAME_GTD("OG AnunobyGTDNew Player Note NYK - SF,PF", PlayerHealthStatus.GAME_TIME_DECISION),
    }

    enum class SanitizeValues(val input: String, val expected: String) {
        PLAYER_NAME("Cade CunninghamPlayer Note DET - PG,SG", "Cade Cunningham"),
        PLAYER_NAME_WITH_GAME("Cade CunninghamPlayer Note DET - PG,SG 7:00 pm vs CHI", "Cade Cunningham"),
        PLAYER_NAME_WITH_INJURY("Zach EdeyINJNo new player Notes MEM - C", "Zach Edey"),
        PLAYER_NAME_WITH_GTD("OG AnunobyGTDNew Player Note NYK - SF,PF", "OG Anunoby"),
        PLAYER_NAME_WITH_INJURY_AND_GAME("Keegan MurrayINJVideo ForecastPlayer Note SAC - SF,PF", "Keegan Murray"),
    }

    @Burst
    @Test
    fun getPlayerHealthStatus(
         playerName: PlayerHealthValues
    ) {
        assertThat(playerName.input.getPlayerHealthStatus()).isEqualTo(playerName.expected)
    }

    @Burst
    @Test
    fun sanitizePlayerName(
        player: SanitizeValues
    ) {
        assertThat(player.input.sanitizePlayerName()).isEqualTo(player.expected)
    }

//    @Test
//    fun sanitizePlayerPositionEligibility() {
//        TODO("Not yet implemented")
//    }
//
//    @Test
//    fun `toPlayerRowRawInfo`() {
//        TODO("Not yet implemented")
//    }
}
